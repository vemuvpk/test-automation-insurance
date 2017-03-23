package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;

@Activity( "Assert Error Rules For Manual Personal Income Page Activity" )
public class AssertErrorRulesForManualPersonalIncomeActivity extends
    AbstractActivity< PersonalIncomePage >
{
    private PersonalIncomePage _page;

    public AssertErrorRulesForManualPersonalIncomeActivity(
        PersonalIncomePage page )
    {
        _page = page;
    }

    @Override
    protected PersonalIncomePage execute()
    {

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.ACCIDENT_TRANSACTION_BASE_PREMIUM_LOCATOR ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.ILLNESS_TRANSACTION_BASE_PREMIUM_LOCATOR ), "E020" ) );
        return new PersonalIncomePage( getWebDriver() );

    }
}
