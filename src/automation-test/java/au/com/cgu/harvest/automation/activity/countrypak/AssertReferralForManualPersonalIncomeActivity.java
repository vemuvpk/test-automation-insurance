package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;

@Activity( "Assert Referral For Manual Personal Income Page Activity" )
public class AssertReferralForManualPersonalIncomeActivity extends
    AbstractActivity< PersonalIncomePage >
{
    private PersonalIncomePage _page;

    public AssertReferralForManualPersonalIncomeActivity(
        PersonalIncomePage page )
    {
        _page = page;
    }

    @Override
    protected PersonalIncomePage execute()
    {
        _page.setAccidentTransactionBasePremium( "111.11" );
        _page.setIllnessTransactionBasePremium( "111.11" );

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.ACCIDENT_TRANSACTION_BASE_PREMIUM_LOCATOR ), "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.ILLNESS_TRANSACTION_BASE_PREMIUM_LOCATOR ), "R062" ) );

        return new PersonalIncomePage( getWebDriver() );

    }
}
