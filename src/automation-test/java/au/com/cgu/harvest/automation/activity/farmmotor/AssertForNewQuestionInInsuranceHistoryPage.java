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

@Activity( "Assert for Mandatory Field Error on New Question in Insurance History Page" )
public class AssertForNewQuestionInInsuranceHistoryPage extends
    AbstractActivity< InsuranceHistoryPage >
{
    private HarvestPage _page;

    public AssertForNewQuestionInInsuranceHistoryPage( HarvestPage page )
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
            .xpath( insuranceHistoryPage.HAVE_THE_QUESTIONS_BEEN_ANSWERED_LOCATOR ) ) );

        return insuranceHistoryPage;

    }
}
