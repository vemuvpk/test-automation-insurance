package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Exercise Error Rules for Countrypak Farm Machinery And Working Dogs Page activity" )
public class ExerciseErrorRulesForFarmMachineryAndWorkingDogsPageActivity extends
    AbstractActivity< FarmMachineryAndWorkingDogsPage >
{
    private HarvestPage _page;

    public ExerciseErrorRulesForFarmMachineryAndWorkingDogsPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMachineryAndWorkingDogsPage execute()
    {
        FarmMachineryAndWorkingDogsPage farmMachineryAndWorkingDogsPage =
            _page.getNavigationTree().navigateToFarmMachinery();

        _page.getNavigationTree().getSection( SectionType.FARM_MACHINERY )
            .containsRule( "E008" );

        farmMachineryAndWorkingDogsPage.setUnspecifiedFarmMachinerySumInsured( "Invalid String" );
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmMachineryAndWorkingDogsPage.FARM_MACHINERY_SUM_INSURED_LOCATOR ),
            "E060" ) );

        return farmMachineryAndWorkingDogsPage;
    }
}
