package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Assert for Mandatory Field Error on Duty of Disclosure Field in Policy Deails page" )
public class AssertMandatoryErrorForDutyOfDisclosureActivity extends AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;

    public AssertMandatoryErrorForDutyOfDisclosureActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        PolicyDetailPage policyDetailPage = _page.getNavigationTree().navigateToPolicyDetails();

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( policyDetailPage.DUTY_OF_DISCLOSURE_LOCATOR ) ) );

        return policyDetailPage;

    }

}
