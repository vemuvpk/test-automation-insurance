package au.com.cgu.harvest.automation.rule;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import au.com.cgu.harvest.automation.WebDriverLauncher;
import au.com.cgu.harvest.automation.browser.TargetWebBrowser;

/**
 * Class manages the driver life cycle for a local, class-loaded WebDriver
 * <p/>
 * User: yurodivuie Date: 3/11/12 Time: 9:56 AM
 */
public class ClassLoadedWebDriverManager extends AbstractWebDriverManager
{

    private static final Logger LOG = Logger.getLogger( ClassLoadedWebDriverManager.class );
    private WebDriver _webDriver;

    /**
     * Creates a new ClassLoadedWebDriverManager with the specified driver.
     * 
     * @param driver
     */
    public ClassLoadedWebDriverManager( TargetWebBrowser targetWebBrowser,
        WebDriverLauncher launcher )
    {
        super( targetWebBrowser, launcher );
    }

    /**
     * Returns the job id (not generally useful for class-loaded drivers, but accessible).
     */
    @Override
    public final String getJobId()
    {
        return ( ( RemoteWebDriver ) getDriver() ).getSessionId().toString();
    }

    @Override
    void reportStartUp()
    {
        LOG.info( "WebDriver ready" );
    }

    @Override
    void reportShutDown()
    {
        LOG.info( "WebDriver shut down" );
    }

}
