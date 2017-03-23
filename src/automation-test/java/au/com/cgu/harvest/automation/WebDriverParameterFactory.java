package au.com.cgu.harvest.automation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class figures out which WebDriver(s) to set up.
 */
public final class WebDriverParameterFactory
{

    private WebDriverParameterFactory()
    {
        throw new IllegalAccessError( "utility class should not be constructed" );
    }

    public static final String BY_CLASS = "byclass";
    public static final String WEBDRIVER_DRIVER = "webdriver";
    public static final String WEBDRIVER_BROWSERS = "webdriver.browsers";

    private static final String CONFIGURED_CLASS_DRIVER = ConfigurationValue.getConfigurationValue(
        WEBDRIVER_DRIVER, AutomationConfiguration.getDefaultWebDriver() );
    private static final String CONFIGURED_REMOTE_BROWSERS = ConfigurationValue
        .getConfigurationValue(
            WEBDRIVER_BROWSERS, AutomationConfiguration.getDefaultWebDriverBrowsers() );

    private static List< String[] > driverTargets;

    public static List< String[] > getDriverTargets()
    {
        if ( driverTargets == null )
        {
            createDriverTargets();
        }
        return driverTargets;
    }

    private static void createDriverTargets()
    {
        if ( TestConfiguration.getCurrentTestConfiguration().getRemoteDriverHost() != null )
        {
            createRemoteDriverTargets();
        }
        else
        {
            createClassDriverTarget();
        }
    }

    private static void createClassDriverTarget()
    {
        createSingleTarget( BY_CLASS, CONFIGURED_CLASS_DRIVER );
    }

    /**
     * These are the standard development target(s) for running a multi-browser test. They can be
     * configured using the webdriver.browsers config value.
     * 
     * @return A list of string arrays; each list element is a paired browser/version.
     */
    private static void createRemoteDriverTargets()
    {
        List< String[] > result = new ArrayList< String[] >();
        String[] targets = CONFIGURED_REMOTE_BROWSERS.split( "," );

        for ( String target : targets )
        {
            result.add( convertToParameters( target ) );
        }

        driverTargets = result;
    }

    private static void createSingleTarget( String... parameters )
    {
        driverTargets = Arrays.asList( new String[][]
        {
            parameters
        } );
    }

    /**
     * Splits a target string into browser/version String pair, with version set to null if version
     * is "*".
     * 
     * @param target
     *            a browser:version string (iexplore:8, for example)
     * @return target split into separate strings for browser and version.
     */
    private static String[] convertToParameters( String target )
    {
        String[] items = target.split( ":" );
        if ( items.length != 2 )
        {
            throw new IllegalArgumentException( "Target " + target
                + " should have one colon in browser:version format." );
        }
        items[ 1 ] = items[ 1 ].replaceAll( "\\*", "" ); // * used to refer to "any browser". Now
                                                         // null is used.
        return items;
    }
}
