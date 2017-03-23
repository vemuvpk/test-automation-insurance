package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;

@Activity( "Assert Mandatory Rules and Complete the insurance history page in a manner that allows a policy to be completed" )
public class AssertMandatoryRulesAndCompleteInsuranceHistoryActivity extends
    AbstractActivity< InsuranceHistoryPage >
{
    private HarvestPage _page;

    public AssertMandatoryRulesAndCompleteInsuranceHistoryActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected InsuranceHistoryPage execute()
    {
        InsuranceHistoryPage insuranceHistoryPage =
            _page.getNavigationTree().navigateToInsuranceHistory();

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( insuranceHistoryPage.DRIVING_CONVICTIONS_LOCATOR ) ) );

        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( insuranceHistoryPage.CRIMINAL_OFFENCES_LOCATOR ) ) );

        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( insuranceHistoryPage.PREVIOUS_INSURANCE_OR_CLAIM_DENIED_LOCATOR ) ) );

        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( insuranceHistoryPage.PRIOR_CLAIMS_LOCATOR ) ) );

        insuranceHistoryPage.setHappyPathValues();

        return insuranceHistoryPage;
    }
}
