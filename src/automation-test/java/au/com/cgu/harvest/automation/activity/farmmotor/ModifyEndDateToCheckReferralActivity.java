package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Modify Start Date of Policy to Check Referrals in Policy Details Page" )
public class ModifyEndDateToCheckReferralActivity extends AbstractActivity< PolicyDetailPage >
{
    private NewBusinessPage _newBusinessPage;
    private PolicyDetailPage _policyPage;
    private HarvestPage _page;
    private String _endDate;
    private String _referralRule;

    public ModifyEndDateToCheckReferralActivity( NewBusinessPage newBusinessPage,
        String endDate, String referralRule )
    {
        _newBusinessPage = newBusinessPage;
        _endDate = endDate;
        _referralRule = referralRule;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _newBusinessPage.setEndDate( _endDate );
        PolicyDetailPage policyDetailPage = _newBusinessPage.modifyRiskDetails();

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.getNavigationTree().POLICY_DETAIL_WITH_REFERRAL_LOCATOR ),
            _referralRule ) );
        return new PolicyDetailPage( getWebDriver() );
    }
}
