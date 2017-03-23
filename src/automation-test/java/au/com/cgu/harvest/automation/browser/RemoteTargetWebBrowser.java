package au.com.cgu.harvest.automation.browser;

import java.util.Map;

/**
 * Represents a target browser/browser version combination. With
 * parallel-webtest, the browser may either be specified by a name (e.g.
 * "firefox") when talking to SauceLabs, or it may be specified by a class name
 * to load a specific local WebDriver instance.
 */
public class RemoteTargetWebBrowser implements TargetWebBrowser
{

    /**
     * What browser?
     */
    private String _browser;

    /**
     * What version?
     */
    private String version;

    private Map< String, Object > customCapabilities;

    /**
     * Create a new target browser/version combination.
     * 
     * @param browser
     * @param version
     */
    public RemoteTargetWebBrowser( String browser, String version,
        Map< String, Object > customCapabilities )
    {
        this._browser = browser;
        this.version = version;
        this.customCapabilities = customCapabilities;
    }

    @Override
    public String getBrowser()
    {
        return _browser;
    }

    @Override
    public String getVersion()
    {
        return version;
    }

    /**
     * Returns the custom capabilities, if any.
     * 
     * @return
     */
    @Override
    public Map< String, Object > getCustomCapabilities()
    {
        return customCapabilities;
    }

    /**
     * Returns true if the browser is iexplore or the version contains the
     * InternetExplorerDriver.
     * 
     * @return
     */
    @Override
    public boolean isInternetExplorer()
    {
        return this._browser.contains( INTERNET_EXPLORER );
    }

    /**
     * Returns true if the browser is firefox or the version contains the
     * FirefoxDriver.
     * 
     * @return
     */
    @Override
    public boolean isFirefox()
    {
        return this._browser.contains( FIREFOX );
    }

    /**
     * Returns true if the browser is chrome or the version contains the
     * ChromeDriver.
     * 
     * @return
     */
    @Override
    public boolean isChrome()
    {
        return this._browser.contains( CHROME );
    }

    /**
     * Returns true if the browser is safari.
     * 
     * @return
     */
    @Override
    public boolean isSafari()
    {
        return this._browser.contains( SAFARI );
    }

    /**
     * Returns true if the target is class loaded (and hence run in a local browser).
     * 
     * @return
     */
    @Override
    public boolean isClassLoaded()
    {
        return false;
    }

    /**
     * Returns true if the target is for a remote browser (run using a Selenium Server).
     * 
     * @return
     */
    @Override
    public boolean isRemote()
    {
        return true;
    }

    /**
     * Returns whether any custom capabilities are specified.
     * 
     * @return
     */
    @Override
    public boolean hasCustomCapabilities()
    {
        return customCapabilities != null && !customCapabilities.isEmpty();
    }

    /**
     * Returns browser:version in string form.
     * 
     * @return A human-readable string representing the parameters used for test
     */
    @Override
    public String humanReadable()
    {
        return _browser + ":" + version;
    }
}
