package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.NavigationTree;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Assert Referral For Intermediary initiated Renewal Activity" )
public class ExerciseReferralRuleForIntermediaryInitiatedRenewalActivity
    extends
    AbstractActivity< FinishPage >
{
    private HarvestPage _page;

    public ExerciseReferralRuleForIntermediaryInitiatedRenewalActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FinishPage execute()
    {
        NavigationTree navTree = _page.getNavigationTree();

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( navTree.POLICY_DETAIL_WITH_REFERRAL_LOCATOR ),
            "R069" ) );

        return new FinishPage( getWebDriver() );

    }

}
