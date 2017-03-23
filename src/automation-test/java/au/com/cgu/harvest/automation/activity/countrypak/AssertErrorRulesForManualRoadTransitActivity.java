package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;

@Activity( "Assert Error Rules For Manual Road Transit Page Activity" )
public class AssertErrorRulesForManualRoadTransitActivity extends
    AbstractActivity< RoadTransitPage >
{
    private RoadTransitPage _page;

    public AssertErrorRulesForManualRoadTransitActivity(
        RoadTransitPage page )
    {
        _page = page;
    }

    @Override
    protected RoadTransitPage execute()
    {

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.TRANSACTION_BASE_PREMIUM ), "E020" ) );
        return new RoadTransitPage( getWebDriver() );

    }
}
