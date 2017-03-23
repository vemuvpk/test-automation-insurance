package au.com.cgu.harvest.automation.browser;

import java.util.Map;

public interface TargetWebBrowser
{
    String FIREFOX = "firefox";
    String INTERNET_EXPLORER = "iexplore";
    String CHROME = "chrome";
    String SAFARI = "safari";
    String BYCLASS = "byclass";

    String getBrowser();

    String getVersion();

    String humanReadable();

    Map< String, Object > getCustomCapabilities();

    boolean isInternetExplorer();

    boolean isFirefox();

    boolean isChrome();

    boolean isSafari();

    boolean isClassLoaded();

    boolean isRemote();

    boolean hasCustomCapabilities();

}
