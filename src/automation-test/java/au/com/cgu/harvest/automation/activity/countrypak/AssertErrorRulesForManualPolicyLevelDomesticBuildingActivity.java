package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelDomesticBuildingPage;

@Activity( "Assert Error Rules For Manual Policy Level Domestic Buildings and Contents Activity" )
public class AssertErrorRulesForManualPolicyLevelDomesticBuildingActivity extends
    AbstractActivity< PolicyLevelDomesticBuildingPage >
{
    private PolicyLevelDomesticBuildingPage _page;

    public AssertErrorRulesForManualPolicyLevelDomesticBuildingActivity(
        PolicyLevelDomesticBuildingPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyLevelDomesticBuildingPage execute()
    {
        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.UNSPECIFIED_VALUABLES_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.SPECIFIED_VALUABLES_TRANSACTION_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        return new PolicyLevelDomesticBuildingPage( getWebDriver() );
    }
}
