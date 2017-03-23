package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelFarmPropertyPage;

@Activity( "Assert Referral For Manual Policy Level Farm Property Activity" )
public class AssertReferralForManualPolicyLevelFarmPropertyActivity extends
    AbstractActivity< PolicyLevelFarmPropertyPage >
{
    private PolicyLevelFarmPropertyPage _page;

    public AssertReferralForManualPolicyLevelFarmPropertyActivity(
        PolicyLevelFarmPropertyPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyLevelFarmPropertyPage execute()
    {
        _page.setSpecifiedItemsTransactionBasePremium( "111.11" );

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.SPECIFIED_ITEMS_TRANSACTION_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        return new PolicyLevelFarmPropertyPage( getWebDriver() );
    }
}
