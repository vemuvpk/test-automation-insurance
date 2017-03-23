// package au.com.cgu.harvest.automation;
//
// import static org.junit.Assert.fail;
//
// import java.io.File;
// import java.io.IOException;
// import java.net.MalformedURLException;
// import java.net.URL;
// import java.util.UUID;
//
// import org.apache.commons.codec.binary.Base64;
// import org.apache.log4j.Logger;
// import org.junit.Before;
// import org.junit.Ignore;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.openqa.selenium.JavascriptExecutor;
// import org.openqa.selenium.Platform;
// import org.openqa.selenium.Proxy;
// import org.openqa.selenium.Proxy.ProxyType;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.firefox.FirefoxProfile;
// import org.openqa.selenium.remote.DesiredCapabilities;
// import org.openqa.selenium.remote.RemoteWebDriver;
// import org.openqa.selenium.remote.ScreenshotException;
//
// import au.com.cgu.harvest.automation.TestConfiguration.DriverMode;
// import au.com.cgu.harvest.automation.TestConfiguration.HostedMode;
// import au.com.cgu.harvest.automation.scenario.AbstractScenario;
//
// import com.google.common.io.Files;
//
// /**
// * THIS ALL NEEDS TO BE REMOVED
// * Tests are now executed directly, and @Rule instances used to control the creation and
// * configuration of the WebDriver
// *
// * @author ben
// */
//
// @Ignore( "Invoked for each @Scenario using reflection" )
// @RunWith( ParallelScenarioRunner.class )
// public class ScenarioTestClass
// {
// private AbstractScenario _scenario;
// private static final Logger LOGGER = Logger.getLogger( ScenarioTestClass.class );
// private TestConfiguration _activeTestConfiguration;
// private Proxy _proxySettings;
//
// WebDriver _driver;
//
// @Before
// public void setupEnvironment()
// {
// initialiseTestConfiguration();
// initialiseDriver();
// }
//
// private void initialiseTestConfiguration()
// {
// if ( TestConfiguration.jvmArgsProvided() )
// {
// LOGGER.info( "Starting WebDriver using JVM Test Configuration" );
// _activeTestConfiguration = TestConfiguration.getJvmConfiguration();
// }
// else
// {
// // LOCAL DEVELOPMENT OPTIONS:
// // LocalJettyConfiguration: Runs against Jetty without the Selenium Server
// // LocalHostedModeConfiguration: Runs against Hosted Mode without the Selenium Server
// // RemoteDriverLocalJettyConfiguration: Runs against Jetty using a local Selenium Server
// // LOGGER.info( "Starting WebDriver using non-JVM Test Configuration" );
// // _activeTestConfiguration =
// // TestConfiguration.getWebsphereSeleniumConfiguration();
// _activeTestConfiguration =
// TestConfiguration.getRemoteDriverLocalJettyConfiguration();
// }
//
// if ( HostOperatingSystem.isWindows() && isLocalBuild() )
// {
// _proxySettings = createIagProxySettings();
// }
// else
// {
// _proxySettings = noProxy();
// }
// }
//
// private boolean isLocalBuild()
// {
// return !_activeTestConfiguration.isCiBuild();
// }
//
// public ScenarioTestClass( AbstractScenario scenario )
// {
// _scenario = scenario;
// }
//
// @Test
// public void run()
// {
// try
// {
// _scenario.setWebDriver( _driver );
// _scenario.setTestConfiguration( _activeTestConfiguration );
// _scenario.run();
// }
// catch ( Exception e )
// {
// maybeSaveScreenshot( e );
// e.printStackTrace();
// fail( e.getMessage() );
// }
// finally
// {
// closeBrowserInstance();
// }
// }
//
// private void closeBrowserInstance()
// {
// if ( keepBrowserOpenOverrideIsSet() )
// {
// return;
// }
//
// try
// {
// ( ( JavascriptExecutor ) _driver )
// .executeScript( "window.onbeforeunload = function(e){};" );
// _driver.quit();
// }
// catch ( Exception e )
// {
// e.printStackTrace();
// }
// }
//
// private boolean keepBrowserOpenOverrideIsSet()
// {
// String overrideValue = System.getenv( "HARVEST_SELENIUM_KEEP_BROWSER_OPEN" );
// return overrideValue != null && overrideValue.equalsIgnoreCase( "true" );
// }
//
// public void maybeSaveScreenshot( Exception webDriverException )
// {
// ScreenshotException screenshotException = findScreenshotException( webDriverException );
//
// if ( screenshotException == null )
// {
// AbstractScenario.getScenarioLogger()
// .trace( "No screenshot was taken" );
// return;
// }
//
// saveScreenshot( screenshotException );
// }
//
// private void saveScreenshot( ScreenshotException screenshotException )
// {
// String data = screenshotException.getBase64EncodedScreenshot();
// byte[] bytes = Base64.decodeBase64( data.getBytes() );
// File file = new File( generateFileName() + ".png" );
// try
// {
// Files.write( bytes, file );
// }
// catch ( IOException e )
// {
// e.printStackTrace();
// fail( e.getMessage() );
// }
// }
//
// private ScreenshotException findScreenshotException( Throwable t )
// {
// if ( t.getCause() == null )
// {
// return null;
// }
//
// if ( t.getCause() != null && t.getCause() instanceof ScreenshotException )
// {
// return ( ScreenshotException ) t.getCause();
// }
// else
// {
// return findScreenshotException( t.getCause() );
// }
// }
//
// private String generateFileName()
// {
// String randomFileName = UUID.randomUUID().toString();
// AbstractScenario.getScenarioLogger()
// .trace( "Exception screenshot: " + randomFileName );
// return randomFileName;
// }
//
// private void initialiseDriver()
// {
// switch ( _activeTestConfiguration.getDriverMode() )
// {
// case LOCAL:
// initialiseFirefoxWebDriver();
// break;
// case REMOTE:
// initialiseRemoteWebDriver( DesiredCapabilities.firefox() );
// break;
// }
// }
//
// private void initialiseFirefoxWebDriver()
// {
// FirefoxProfile profile = createFirefoxProfile();
// if ( _activeTestConfiguration.getHostedMode() == HostedMode.TRUE )
// {
// try
// {
// File gwtPlugin = new File( AutomationConfiguration.getGwtPluginPath() );
// profile.addExtension( gwtPlugin );
// profile.setPreference( "gwt-dev-plugin.accessList",
// AutomationConfiguration.getGwtPluginAccessList() );
// _driver = new FirefoxDriver( profile );
// }
// catch ( IOException e )
// {
// throw new RuntimeException( "Failed to load GWT plugin" );
// }
// }
// else
// {
//
// _driver = new FirefoxDriver( profile );
// }
// }
//
// private FirefoxProfile createFirefoxProfile()
// {
// FirefoxProfile profile = new FirefoxProfile();
// if ( _proxySettings != null )
// {
// profile.setProxyPreferences( _proxySettings );
// }
// return profile;
// }
//
// private Proxy createIagProxySettings()
// {
// Proxy proxy = new Proxy();
// proxy.setHttpProxy( AutomationConfiguration.getProxyServer() );
// proxy.setNoProxy( AutomationConfiguration.getProxyExclusions() );
// return proxy;
// }
//
// private Proxy noProxy()
// {
// Proxy proxy = new Proxy();
// proxy.setProxyType( ProxyType.DIRECT );
// return proxy;
// }
//
// private void initialiseRemoteWebDriver( DesiredCapabilities capabilities )
// {
// try
// {
// LOGGER.info( "Initialising REMOTE web driver" );
// capabilities.setPlatform( Platform.WINDOWS );
// capabilities.setCapability( "firefox_profile", createFirefoxProfile() );
// URL remoteUrl = new URL( _activeTestConfiguration.getRemoteDriverHost() );
// _driver = new RemoteWebDriver( remoteUrl, capabilities );
// }
// catch ( MalformedURLException e )
// {
// e.printStackTrace();
// }
// }
// }
