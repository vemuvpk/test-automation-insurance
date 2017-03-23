package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Assert Error Rules For Manual Farm Property Activity" )
public class AssertErrorRulesForManualFarmPropertyActivity extends
    AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyPage _page;

    public AssertErrorRulesForManualFarmPropertyActivity(
        FarmPropertyPage page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.UNSPECIFIED_FARM_BUILDING_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.IN_ANY_ONE_BUILDING_BASE_PREMIUM_LOCATOR ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.ABOVE_GROUND_FARM_CONTENTS_BASE_PREMIUM_LOCATOR ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.FARM_IMPROVEMENTS_TRANSACTION_BASE_PREMIUM_LOCATOR ), "E020" ) );
        return new FarmPropertyPage( getWebDriver() );

    }
}
