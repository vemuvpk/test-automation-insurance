package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;

@Activity( "Assert Error Rules For Manual Farm Machinery Page Activity" )
public class AssertErrorRulesForManualFarmMachineryPageActivity extends
    AbstractActivity< FarmMachineryAndWorkingDogsPage >
{
    private FarmMachineryAndWorkingDogsPage _page;

    public AssertErrorRulesForManualFarmMachineryPageActivity(
        FarmMachineryAndWorkingDogsPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMachineryAndWorkingDogsPage execute()
    {

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.FARM_MACHINERY_BASE_PREMIUM ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.UNSPECIFIED_FARM_MACHINERY_BASE_PREMIUM ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.WORKING_DOGS_BASE_PREMIUM ), "E020" ) );
        return new FarmMachineryAndWorkingDogsPage( getWebDriver() );

    }
}
