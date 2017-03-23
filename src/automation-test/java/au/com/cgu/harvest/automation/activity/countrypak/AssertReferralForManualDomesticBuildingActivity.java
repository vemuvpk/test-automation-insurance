package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;

@Activity( "Assert Referral For Manual Domestic Buildings and Contents Activity" )
public class AssertReferralForManualDomesticBuildingActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DomesticBuildingAndContentsPage _page;

    public AssertReferralForManualDomesticBuildingActivity(
        DomesticBuildingAndContentsPage page )
    {
        _page = page;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        _page.setAdditionalBusinessContentsBasePremium( "111.11" );

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.ADDITIONAL_BUSINESS_CONTENTS_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        return new DomesticBuildingAndContentsPage( getWebDriver() );

    }
}
