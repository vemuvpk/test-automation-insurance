package au.com.cgu.harvest.automation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import au.com.cgu.harvest.automation.TestConfiguration.HostedMode;
import au.com.cgu.harvest.automation.browser.TargetWebBrowser;
import au.com.cgu.harvest.automation.driver.CapturingRemoteWebDriver;

/**
 * This class is responsible for launching single WebDriver instances. Note that it uses the logger
 * passed in - this allows for breaking up the logs by target browser.
 */
public class WebDriverLauncher
{

    /**
     * Maximum retries for getting a remote web driver
     */
    public static final int MAX_RETRIES = 5;

    /**
     * Unique identifier for this job run. Global value for entire suite execution
     */
    protected static final String uniqueId = String.valueOf( UUID.randomUUID() );

    private Logger _logger = null;
    private WebDriver _driver = null;
    private Proxy _proxySettings = null;

    public WebDriverLauncher( Logger logger )
    {
        _logger = logger;
        initialiseProxySettings();
    }

    public WebDriver launchDriver( TargetWebBrowser targetWebBrowser )
    {
        if ( targetWebBrowser.isRemote() )
        {
            return getRemoteDriver( "default job name", targetWebBrowser );
        }
        if ( targetWebBrowser.isClassLoaded() )
        {
            return getClassLoadedDriver( targetWebBrowser );
        }
        throw new RuntimeException( "Unsupported driver type" );
    }

    /**
     * Returns a driver for a local, class-loaded browser.
     * 
     * @param targetWebBrowser
     * @return
     */
    private WebDriver getClassLoadedDriver( TargetWebBrowser targetWebBrowser )
    {
        _logger.trace( String.format( "Initializing WebDriver by specified class: %s",
            targetWebBrowser.humanReadable() ) );

        DesiredCapabilities defaultCapabilities =
            constructDefaultCapabilities( "N/A", targetWebBrowser );
        DesiredCapabilities mergedCapabilities =
            mergeDefaultAndCustomCapabilities( defaultCapabilities,
                targetWebBrowser.getCustomCapabilities() );

        try
        {
            Class< ? > browserClass = Class.forName( targetWebBrowser.getBrowser() );
            Constructor< ? > constructorWithCapabilities =
                browserClass.getDeclaredConstructor( Capabilities.class );
            _driver = ( WebDriver ) constructorWithCapabilities.newInstance( mergedCapabilities );
        }
        catch ( Exception e )
        {
            _logger.error( "Exception while loading class-loaded driver", e );
            if ( e.getMessage() != null
                && e.getMessage().contains( "Unable to bind to locking port" ) )
            {
                _logger
                    .error( "Locking port error may be caused by ephemeral port exhaustion.  Try reducing the number of threads." );
            }
            throw new WebDriverException( "Unable to load target WebDriver class: "
                + targetWebBrowser.getBrowser(), e );
        }

        verifyDriverNotNull( _driver );
        return _driver;
    }

    /**
     * Returns a driver for a remote driver.
     * 
     * @param jobName
     *            Name of the job as it will appear in the remote server
     * @param target
     * @return
     */
    private WebDriver getRemoteDriver( String jobName, TargetWebBrowser target )
    {
        verifyHostNameIsSpecified();
        createRemoteWebDriver( jobName, target );
        verifyDriverNotNull( _driver );
        return _driver;
    }

    private void verifyHostNameIsSpecified()
    {
        // TODO: Currently provided by TestConfiguration - Replace or use this approach?
        // String webTestHostName =
        // ConfigurationValue.getConfigurationValue( SystemName.WEBTEST_HOSTNAME, null );

        String webTestHostName =
            TestConfiguration.getCurrentTestConfiguration().getSunriseExecutiveUrl();
        if ( webTestHostName == null )
        {
            throw new WebDriverException(
                "No hostname is specified for remote test. Please specify a WEBTEST_HOSTNAME value." );
        }
    }

    private void verifyDriverNotNull( WebDriver driver )
    {
        if ( driver == null )
        {
            throw new WebDriverException( "Failed to provision WebDriver." );
        }
    }

    private void createRemoteWebDriver( String jobName, TargetWebBrowser target )
    {
        for ( int attempt = 1; attempt <= MAX_RETRIES && _driver == null; attempt++ )
        {
            _logger.trace( String.format( "Provisioning RemoteWebDriver attempt %s for job %s",
                attempt, jobName ) );

            try
            {
                buildDriverWithCapabilities( jobName, target );
                verifyDriverIsValid( _driver );
            }
            catch ( WebDriverException e )
            {
                _logger.error( String.format( "Unable to launch RemoteWebDriver: %s",
                    e.getMessage() ) );
                _driver = null;
            }
        }
    }

    private void buildDriverWithCapabilities( String jobName, TargetWebBrowser target )
    {
        try
        {
            DesiredCapabilities capabilities =
                constructDefaultCapabilities( "default job name", target );
            capabilities =
                mergeDefaultAndCustomCapabilities( capabilities, target.getCustomCapabilities() );
            _driver =
                new CapturingRemoteWebDriver( new URL( TestConfiguration
                    .getCurrentTestConfiguration()
                    .getRemoteDriverHost() ),
                    capabilities );
        }
        catch ( MalformedURLException e )
        {

            e.printStackTrace();
            throw new RuntimeException( e );
        }
    }

    private DesiredCapabilities constructDefaultCapabilities( String jobName,
        TargetWebBrowser target )
    {

        DesiredCapabilities capabilities = new DesiredCapabilities(
            target.getBrowser(), target.getVersion(), getPlatform( target ) );

        // Add capabilities for custom profile to support hosted mode (includes GWT plugin)
        // and the IAG proxy server if configured by system properties
        if ( target.isFirefox() )
        {
            capabilities.setCapability( "firefox_profile", createFirefoxProfile() );
        }

        // String seleniumVersion =
        // ConfigurationValue.getConfigurationValue( "REMOTE_SERVER_VERSION", "2.20.0" );
        // capabilities.setCapability( "name", jobName );
        // capabilities.setCapability( "tags", SystemName.getSystemName() );
        // capabilities.setCapability( "build", uniqueId );
        // capabilities.setCapability( "selenium-version", seleniumVersion );

        return capabilities;
    }

    private void initialiseProxySettings()
    {
        if ( HostOperatingSystem.isWindows() && isLocalBuild() )
        {
            _proxySettings = createIagProxySettings();
        }
        else
        {
            _proxySettings = noProxy();
        }
    }

    private boolean isLocalBuild()
    {
        return !TestConfiguration.getCurrentTestConfiguration().isCiBuild();
    }

    private FirefoxProfile createFirefoxProfile()
    {
        FirefoxProfile profile = new FirefoxProfile();
        if ( _proxySettings != null )
        {
            // profile.setProxyPreferences( _proxySettings );
        }
        if ( TestConfiguration.getCurrentTestConfiguration().getHostedMode() == HostedMode.TRUE )
        {
            attachDevModePlugin( profile );
        }
        return profile;

    }

    private void attachDevModePlugin( FirefoxProfile profile )
    {
        try
        {
            File gwtPlugin = new File( AutomationConfiguration.getGwtPluginPath() );
            profile.addExtension( gwtPlugin );
            profile.setPreference( "gwt-dev-plugin.accessList",
                AutomationConfiguration.getGwtPluginAccessList() );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Failed to load GWT plugin" );
        }
    }

    private Proxy createIagProxySettings()
    {
        Proxy proxy = new Proxy();
        proxy.setHttpProxy( AutomationConfiguration.getProxyServer() );
        proxy.setNoProxy( AutomationConfiguration.getProxyExclusions() );
        return proxy;
    }

    private Proxy noProxy()
    {
        Proxy proxy = new Proxy();
        proxy.setProxyType( ProxyType.DIRECT );
        return proxy;
    }

    private Platform getPlatform( TargetWebBrowser target )
    {
        if ( ";".equals( System.getProperty( "path.separator" ) ) )
        {
            return Platform.WINDOWS;
        }
        return Platform.MAC;
    }

    private DesiredCapabilities mergeDefaultAndCustomCapabilities(
        DesiredCapabilities capabilities, Map< String, Object > customCapabilities )
    {
        if ( customCapabilities != null )
        {
            for ( Map.Entry< String, Object > customCapability : customCapabilities.entrySet() )
            {
                capabilities.setCapability( customCapability.getKey(), customCapability.getValue() );
            }
        }
        return capabilities;
    }

    private void verifyDriverIsValid( WebDriver driver ) throws WebDriverException
    {
        if ( driver.getWindowHandle() != null )
        {
            _logger.debug( "Successfully launched RemoteWebDriver" );
        }
        else
        {
            throw new WebDriverException( "driver.getWindowHandle() returned null." );
        }
    }

}
