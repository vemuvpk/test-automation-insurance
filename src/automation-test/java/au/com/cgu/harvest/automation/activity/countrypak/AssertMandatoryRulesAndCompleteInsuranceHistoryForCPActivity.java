package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;

@Activity( "Assert Mandatory Rules and Complete the insurance history page in a manner that allows a policy to be completed" )
public class AssertMandatoryRulesAndCompleteInsuranceHistoryForCPActivity extends
    AbstractActivity< CountrypakInsuranceHistoryPage >
{
    private HarvestPage _page;

    public AssertMandatoryRulesAndCompleteInsuranceHistoryForCPActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected CountrypakInsuranceHistoryPage execute()
    {
        CountrypakInsuranceHistoryPage insuranceHistoryPage =
            _page.getNavigationTree().navigateToCountrypakInsuranceHistory();

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( insuranceHistoryPage.ANY_OTHER_RELEVANT_FACTS_LOCATOR ) ) );

        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( insuranceHistoryPage.CRIMINAL_OFFENCES_LOCATOR ) ) );

        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( insuranceHistoryPage.DECLINE_INSURANCE_LOCATOR ) ) );

        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( insuranceHistoryPage.IS_THE_PROPERTY_TO_BE_INSURED_LOCATOR ) ) );

        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( insuranceHistoryPage.PREVIOUS_LOSS_OR_DAMAGE_TO_PROPERTY_LOCATOR ) ) );

        insuranceHistoryPage.setHappyPathValues();

        return insuranceHistoryPage;
    }
}
