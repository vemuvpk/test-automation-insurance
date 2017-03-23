package au.com.cgu.harvest.automation.rule;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import au.com.cgu.harvest.automation.WebDriverLauncher;
import au.com.cgu.harvest.automation.browser.TargetWebBrowser;

/**
 * Class manages the driver life cycle for a RemoteWebDriver.
 * <p/>
 * User: yurodivuie Date: 3/11/12 Time: 9:56 AM
 */
public class RemoteWebDriverManager extends AbstractWebDriverManager
{

    private static final Logger LOG = Logger.getLogger( RemoteWebDriverManager.class );

    private String _jobName;

    /**
     * Constructs a new RemoteWebDriverManager (typically used for Sauce Labs browsers).
     * 
     * @param driver
     */
    public RemoteWebDriverManager( String jobName, TargetWebBrowser targetWebBrowser,
        WebDriverLauncher launcher )
    {
        super( targetWebBrowser, launcher );
        _jobName = jobName;
    }

    /**
     * Returns the job URL. Constructed dynamically.
     */
    @Override
    public final String getJobUrl()
    {
        return getJobId();
    }

    /**
     * Returns the SauceLabs job id.
     */
    @Override
    public final String getJobId()
    {
        return ( ( RemoteWebDriver ) getDriver() ).getSessionId().toString();
    }

    @Override
    void reportStartUp()
    {
        LOG.info( String.format( "RemoteWebDriver ready.  Session ID [%s]", getJobUrl() ) );
    }

    @Override
    void reportShutDown()
    {
        LOG.info( "RemoteWebDriver shut down." );
    }
}
