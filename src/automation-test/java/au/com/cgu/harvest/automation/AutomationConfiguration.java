package au.com.cgu.harvest.automation;

import java.io.IOException;
import java.util.Properties;

public class AutomationConfiguration
{
    private static final String PROXY_SERVER = "proxy.server";
    private static final String PROXY_EXCLUSIONS = "proxy.exclusions";
    private static final String GWT_PLUGIN_PATH = "gwt.plugin.path";
    private static final String GWT_PLUGIN_ACCESS_LIST = "gwt.plugin.access.list";
    private static final String DEFAULT_WEBDRIVER = "webdriver";
    private static final String DEFAULT_WEBDRIVER_BROWSERS = "webdriver.browsers";

    private static Properties AutomationProperties;

    static
    {
        AutomationProperties = new Properties();
        try
        {
            AutomationProperties.load( AutomationConfiguration.class
                .getResourceAsStream( "/automation.properties" ) );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Failed to load automation.properties" );
        }
    }

    public static String getProxyServer()
    {
        return AutomationProperties.getProperty( PROXY_SERVER );
    }

    public static String getProxyExclusions()
    {
        return AutomationProperties.getProperty( PROXY_EXCLUSIONS );
    }

    public static String getGwtPluginPath()
    {
        return AutomationProperties.getProperty( GWT_PLUGIN_PATH );
    }

    public static String getGwtPluginAccessList()
    {
        return AutomationProperties.getProperty( GWT_PLUGIN_ACCESS_LIST );
    }

    public static String getDefaultWebDriver()
    {
        return AutomationProperties.getProperty( DEFAULT_WEBDRIVER );
    }

    public static String getDefaultWebDriverBrowsers()
    {
        return AutomationProperties.getProperty( DEFAULT_WEBDRIVER_BROWSERS );
    }
}
