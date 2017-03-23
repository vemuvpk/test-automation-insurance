package au.com.cgu.harvest.automation.scenario;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.openqa.selenium.WebDriver;

import au.com.cgu.harvest.automation.DescriptivelyParameterized;
import au.com.cgu.harvest.automation.WebDriverManager;
import au.com.cgu.harvest.automation.WebDriverManagerFactory;
import au.com.cgu.harvest.automation.WebDriverParameterFactory;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.browser.TargetWebBrowser;
import au.com.cgu.harvest.automation.browser.TargetWebBrowserFactory;
import au.com.cgu.harvest.automation.rule.MethodTimer;

public abstract class AbstractScenario
{
    @Rule
    public TestName _name = new TestName();

    @Rule
    public TestRule _localTestWatcherChain;

    private static ThreadLocal< TestRule > testWatcherChain = new ThreadLocal< TestRule >();
    private static ThreadLocal< WebDriverManager > webDriverManager =
        new ThreadLocal< WebDriverManager >();
    private static ThreadLocal< Logger > scenarioLogger = new ThreadLocal< Logger >();

    private Logger _infrastructureLogger;
    private TargetWebBrowser _targetWebBrowser;

    public void initialise( String browser, String version )
    {
        initialise( browser, version, new HashMap< String, Object >() );
    }

    public void initialise( String browser, String version, Map< String, Object > customCapabilities )
    {
        customCapabilities.put( "plugins.hide_infobar_for_missing_plugin", "true" );

        _targetWebBrowser = TargetWebBrowserFactory.getTargetWebBrowser(
            browser, version, customCapabilities );
        _infrastructureLogger = createInfrastructureLogger();
        scenarioLogger.set( createScenarioLogger() );
        _infrastructureLogger.debug( "Initialising Scenario: " + getClass().getName() );
        initializeJUnitRules();
    }

    /**
     * ` Feeds in the list of target browsers. This might be a single local browser, or one or more
     * remote instances.
     * 
     * @see WebDriverParameterFactory
     */
    @DescriptivelyParameterized.Parameters
    public static List< String[] > configureWebDriverTargets() throws IOException
    {
        return WebDriverParameterFactory.getDriverTargets();
    }

    @After
    public void cleanUpDriverWindows()
    {
        reduceToOneWindow( getDriverManager().getDriver() );
        _infrastructureLogger.debug( "AFTER Scenario" );
    }

    public static void reduceToOneWindow( WebDriver driver )
    {
        if ( driver != null && driver.getWindowHandles().size() > 1 )
        {
            String firstHandle = ( String ) driver.getWindowHandles().toArray()[ 0 ];
            for ( String handle : driver.getWindowHandles() )
            {
                if ( !handle.equals( firstHandle ) )
                {
                    driver.switchTo().window( handle );
                    driver.close();
                }
            }
            driver.switchTo().window( firstHandle );
        }
    }

    /**
     * This is the target browser/version for this test. See the WebDriverParameterFactory and
     * WebDriverLauncher for more details.
     */
    public final TargetWebBrowser getTargetWebBrowser()
    {
        return _targetWebBrowser;
    }

    /**
     * Returns the SauceLabs job URL (if there is one). Constructed dynamically.
     */
    public final String getJobURL()
    {
        return getDriverManager().getJobUrl();
    }

    /**
     * Returns the SauceLabs job id (if there is one).
     */
    public final String getJobId()
    {
        return getDriverManager().getJobId();
    }

    /**
     * Simple utility method - sleeps for the specified number of milliseconds.
     */
    public void pause( int millisecs )
    {
        try
        {
            Thread.sleep( millisecs );
        }
        catch ( InterruptedException e )
        {
            _infrastructureLogger.debug( "Thread sleep interrupted", e );
        }
    }

    /**
     * This is the logger you should use if you want per-browser instance piping to work correctly.
     * When you run tests in parallel, if you just use a local logger, all of the parallel
     * executions will get intermixed.
     */
    public Logger getLogger()
    {
        return _infrastructureLogger;
    }

    /**
     * The name of the job, as reported to SauceLabs. Includes the user name and the name of the
     * class.
     */
    public String getJobName()
    {
        return this.getClass().getSimpleName();
    }

    private Logger createInfrastructureLogger()
    {
        String logName = String.format( "[%s] %s",
            this.getClass().getName(),
            getTargetWebBrowser().humanReadable() );
        return Logger.getLogger( logName );
    }

    private Logger createScenarioLogger()
    {
        String logName = String.format( "[%s]",
            this.getClass().getName() );
        return Logger.getLogger( logName );
    }

    /***************************************
     * static ThreadLocal Accessors
     ***************************************/

    private void setTestWatcherChain( TestRule rule )
    {
        testWatcherChain.set( rule );
    }

    private TestRule getTestWatcherChain()
    {
        return testWatcherChain.get();
    }

    private void setDriverManager( WebDriverManager driverManager )
    {
        AbstractScenario.webDriverManager.set( driverManager );
    }

    private WebDriverManager getDriverManager()
    {
        return webDriverManager.get();
    }

    public static void setScenarioLogger( Logger logger )
    {
        AbstractScenario.scenarioLogger.set( logger );
    }

    public static Logger getScenarioLogger()
    {
        return scenarioLogger.get();
    }

    /***************************************
     * JUnit Rule Management
     ***************************************/

    /**
     * Sets the local test watcher chain, initializing if necessary.
     */
    private void initializeJUnitRules()
    {
        _localTestWatcherChain = getTestWatcherChain();
        if ( _localTestWatcherChain == null )
        {
            _localTestWatcherChain = createTestWatcherChain();
            setTestWatcherChain( _localTestWatcherChain );
        }
    }

    /**
     * Creates the rule chain that will be used for all tests
     * 
     * @return
     */
    private TestRule createTestWatcherChain()
    {
        RuleChain chain = createStandardRuleChain();
        return chain;
    }

    /**
     * Creates the standard rule chain, which only tracks the driver and times test methods.
     * 
     * @return
     */
    private RuleChain createStandardRuleChain()
    {

        // WebDriverManager is created first and executed last, so that driver is available first
        // and removed last.
        // BEN TEMPORARY HACK CWC-384
        // RetryRule retryRule = new RetryRule( 2 ); // Trying RetryRule to Retry Failing tests
        // RetryRule retryRule = new RetryRule( 1 ); // Trying RetryRule to Retry Failing tests
        WebDriverManager manager = createDriverManager();
        MethodTimer methodTimer = new MethodTimer();

        // return RuleChain.outerRule( retryRule ) // Outer rule is executed last.
        // .around( manager ).around( methodTimer );
        return RuleChain.outerRule( manager ).around( methodTimer );
    }

    /**
     * Creates the driver manager with a new WebDriver driver.
     * 
     * @return A WebDriverManager, which provides the manager to the tests and handles cleanup after
     *         the tests have completed.
     */
    private WebDriverManager createDriverManager()
    {
        WebDriverManagerFactory managerFactory =
            new WebDriverManagerFactory( getLogger() );
        WebDriverManager manager = managerFactory.getManager( getJobName(), _targetWebBrowser );
        setDriverManager( manager );
        return manager;
    }

    public WebDriver getWebDriver()
    {
        return getDriverManager().getDriver();
    }

    protected < PAGE > PAGE performActivity( AbstractActivity< PAGE > activity )
    {
        activity.setScenario( this );
        return activity.perform();
    }

    private void beforeRunning()
    {
        try
        {
            writeDescription();
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    public String getDescription()
    {
        Scenario scenario = getClass().getAnnotation( Scenario.class );
        if ( scenario == null )
        {
            throw new RuntimeException(
                "Scenarios must be annotated with a description using the @Scenario annotation" );
        }

        return scenario.value();
    }

    private void writeDescription()
    {
        _infrastructureLogger.debug( "Scenario: " + getDescription() );
    }
}
