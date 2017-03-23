package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Abort the current transaction" )
public class AbortActivity extends
    AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;
    private WebDriver _driver;

    public AbortActivity( HarvestPage harvestPage )
    {
        _page = harvestPage;
    }

    @Override
    protected NewBusinessPage execute()
    {
        ( ( JavascriptExecutor ) getWebDriver() )
            .executeScript( "window.confirm = function(msg){return true;};" );
        _page.getToolbar().abort();
        return new NewBusinessPage( getWebDriver() );
    }
}
