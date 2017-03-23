package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;

@Activity( "Assert Error Rules For Manual Machinery Breakdown Page Activity" )
public class AssertErrorRulesForManualMachineryBreakdownActivity extends
    AbstractActivity< MachineryBreakdownPage >
{
    private MachineryBreakdownPage _page;

    public AssertErrorRulesForManualMachineryBreakdownActivity(
        MachineryBreakdownPage page )
    {
        _page = page;
    }

    @Override
    protected MachineryBreakdownPage execute()
    {

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.BLANKET_COVER_BASE_PREMIUM ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.SPECIFIED_ITEMS_BASE_PREMIUM ), "E020" ) );
        return new MachineryBreakdownPage( getWebDriver() );

    }
}
