package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;

@Activity( "Assert Referral For Manual Road Transit Page Activity" )
public class AssertReferralForManualRoadTransitActivity extends
    AbstractActivity< RoadTransitPage >
{
    private RoadTransitPage _page;

    public AssertReferralForManualRoadTransitActivity(
        RoadTransitPage page )
    {
        _page = page;
    }

    @Override
    protected RoadTransitPage execute()
    {
        _page.setTransactionBasePremium( "111.11" );

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.TRANSACTION_BASE_PREMIUM ), "R062" ) );

        return new RoadTransitPage( getWebDriver() );

    }
}
