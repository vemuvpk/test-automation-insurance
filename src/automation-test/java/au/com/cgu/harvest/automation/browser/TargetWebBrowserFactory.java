package au.com.cgu.harvest.automation.browser;

import java.util.Map;

public class TargetWebBrowserFactory
{
    private TargetWebBrowserFactory()
    {
        throw new IllegalAccessError( "Utility class should not be constructed" );
    }

    public static TargetWebBrowser getTargetWebBrowser( String browser, String version,
        Map< String, Object > customCapabilities )
    {
        TargetWebBrowser target;
        if ( TargetWebBrowser.BYCLASS.equalsIgnoreCase( browser ) )
        {
            target = new ClassLoadedTargetWebBrowser( version, customCapabilities );
        }
        else
        {
            target = new RemoteTargetWebBrowser( browser, version, customCapabilities );
        }
        return target;
    }
}
