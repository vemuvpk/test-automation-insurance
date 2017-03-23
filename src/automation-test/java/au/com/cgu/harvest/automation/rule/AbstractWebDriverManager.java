package au.com.cgu.harvest.automation.rule;

import org.junit.runner.Description;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import au.com.cgu.harvest.automation.WebDriverLauncher;
import au.com.cgu.harvest.automation.WebDriverManager;
import au.com.cgu.harvest.automation.browser.TargetWebBrowser;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;

/**
 * Abstract class for managing the driver life cycle. Supplies default implementation of getJobUrl
 * and getJobId, which can be overridden if the specific implementation supports these methods.
 * <p/>
 * User: yurodivuie Date: 3/11/12 Time: 9:56 AM
 */
abstract class AbstractWebDriverManager extends ClassFinishRule implements WebDriverManager
{
    private static final ThreadLocal< WebDriver > _webDriverInstances =
        new ThreadLocal< WebDriver >();
    private TargetWebBrowser _targetBrowser;
    private WebDriver _driver;
    private WebDriverLauncher _launcher;

    public AbstractWebDriverManager( TargetWebBrowser targetWebBrowser, WebDriverLauncher launcher )
    {

        _targetBrowser = targetWebBrowser;
        _launcher = launcher;
        // this.driver.manage().timeouts().implicitlyWait( 20, TimeUnit.SECONDS );
        reportStartUp();
    }

    @Override
    protected void classFinished( Description description )
    {
        try
        {
            // // PH-1473: There's a fixed 2 second delay when trying to detect an alert like this
            // on every
            // // page load. It's there because it can take Firefox up to 2 seconds to propagate the
            // event
            // // to webdriver.
            //
            // long alertStartTime = System.currentTimeMillis();
            // try
            // {
            // Alert alert1 = driver.switchTo().alert();
            // String alertText = alert1.getText().trim();
            // AbstractScenario.getScenarioLogger().trace( "**********" + alertText + "********" );
            // if ( _driver.findElements( By.xpath( POPUP_DIALOG_CONTENT ) ).size() > 0 )
            // {
            // ApplicationErrorPopup _errorPopup = new ApplicationErrorPopup( driver );
            // AbstractScenario.getScenarioLogger().trace( _errorPopup.getError() );
            // }
            // }
            // catch ( NoAlertPresentException e )
            // {
            // long alertEndTime = System.currentTimeMillis();
            // System.out.println( "Time waiting for page alerts "
            // + ( alertEndTime - alertStartTime ) + " milliseconds" );

            ( ( JavascriptExecutor ) _driver )
                .executeScript( "window.onbeforeunload=function(e){};" );
            _driver.quit();

            reportShutDown();

        }
        catch ( Exception e )
        {
            AbstractScenario.getScenarioLogger()
                .trace( "Failed to kill webdriver instance: " );
            e.printStackTrace();
            throw new RuntimeException( e );
        }
        finally
        {
            _driver = null;
        }

    }

    /**
     * Provides WebDriver to tests using this rule to manage driver LifeCycle.
     * 
     * @return
     */
    @Override
    public final WebDriver getDriver()
    {
        if ( _driver == null )
        {
            _driver = _launcher.launchDriver( _targetBrowser );
        }

        return _driver;
    }

    /**
     * Returns jobUrl for remote jobs; null otherwise.
     * 
     * @return
     */
    @Override
    public String getJobUrl()
    {
        return null;
    }

    /**
     * Returns the jobId if one exists for this job.
     */
    @Override
    public String getJobId()
    {
        return null;
    }

    abstract void reportStartUp();

    abstract void reportShutDown();

}
