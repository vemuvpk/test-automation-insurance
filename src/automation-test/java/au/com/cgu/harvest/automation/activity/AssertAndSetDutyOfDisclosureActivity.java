package au.com.cgu.harvest.automation.activity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Assert E006 on Duty of Disclosure field and set Duty of Disclosere to Yes" )
public class AssertAndSetDutyOfDisclosureActivity extends AbstractActivity< PolicyDetailPage >
{
    private PolicyDetailPage _page;

    public AssertAndSetDutyOfDisclosureActivity( PolicyDetailPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By.xpath( _page.DUTY_OF_DISCLOSURE_LOCATOR ) ) );

        _page.setDutyOfDisclosure( "true" );
        return _page;

    }

}
