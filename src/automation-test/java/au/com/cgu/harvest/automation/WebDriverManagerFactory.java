package au.com.cgu.harvest.automation;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;

import au.com.cgu.harvest.automation.browser.TargetWebBrowser;
import au.com.cgu.harvest.automation.rule.ClassLoadedWebDriverManager;
import au.com.cgu.harvest.automation.rule.RemoteWebDriverManager;

public class WebDriverManagerFactory
{
    private TestConfiguration _testConfiguration;
    private WebDriverLauncher _launcher;
    private Logger _logger;

    public WebDriverManagerFactory( Logger logger )
    {
        _launcher = new WebDriverLauncher( logger );
        _logger = logger;
    }

    public WebDriverManager getManager( String jobName, TargetWebBrowser targetWebBrowser )
    {
        WebDriverManager manager;

        _logger.debug( String.format( "Attempting to create driver: %s",
            targetWebBrowser.humanReadable() ) );

        if ( targetWebBrowser.isClassLoaded() )
        {
            manager = new ClassLoadedWebDriverManager( targetWebBrowser, _launcher );
        }
        else if ( targetWebBrowser.isRemote() )
        {
            manager = new RemoteWebDriverManager( jobName, targetWebBrowser, _launcher );
        }
        else
        {
            throw new WebDriverException( targetWebBrowser.humanReadable() + " not a valid driver." );
        }

        return manager;
    }
}
