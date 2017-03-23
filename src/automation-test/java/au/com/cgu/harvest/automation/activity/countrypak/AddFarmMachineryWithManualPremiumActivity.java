package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Premiums in farm Machinery and Working Dogs page Activity" )
public class AddFarmMachineryWithManualPremiumActivity extends
    AbstractActivity< FarmMachineryAndWorkingDogsPage >
{
    private HarvestPage _page;

    public AddFarmMachineryWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMachineryAndWorkingDogsPage execute()
    {
        FarmMachineryAndWorkingDogsPage farmMachineryAndWorkingDogsPage =
            _page.getNavigationTree().navigateToFarmMachinery();

        farmMachineryAndWorkingDogsPage.setUnspecifiedFarmMachinerySumInsured( "5000" );
        farmMachineryAndWorkingDogsPage.getFarmMachineryGrid().addRow( "Machinery-1", "500" );
        farmMachineryAndWorkingDogsPage.getFarmMachineryGrid().addRow( "machinery-2", "550" );
        farmMachineryAndWorkingDogsPage.getFarmDogsGrid().addRow( "Dog-1", "800" );
        farmMachineryAndWorkingDogsPage.getFarmDogsGrid().addRow( "Dog-2", "600" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.FARM_MACHINERY );
        section.isTaken();

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmMachineryAndWorkingDogsPage.FARM_MACHINERY_BASE_PREMIUM ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( farmMachineryAndWorkingDogsPage.UNSPECIFIED_FARM_MACHINERY_BASE_PREMIUM ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( farmMachineryAndWorkingDogsPage.WORKING_DOGS_BASE_PREMIUM ),
            "E020" ) );

        farmMachineryAndWorkingDogsPage.setUnspecifiedFarmMachineryBasePremium( "55.55" );
        farmMachineryAndWorkingDogsPage.setFarmMachineryBasePremium( "55.55" );
        farmMachineryAndWorkingDogsPage.setWorkingDogsBasePremium( "55.55" );
        // Check for R062
        wait.until( Rule.isDisplayed(
            By.xpath( farmMachineryAndWorkingDogsPage.FARM_MACHINERY_BASE_PREMIUM ), "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( farmMachineryAndWorkingDogsPage.UNSPECIFIED_FARM_MACHINERY_BASE_PREMIUM ),
            "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( farmMachineryAndWorkingDogsPage.WORKING_DOGS_BASE_PREMIUM ), "R062" ) );

        return farmMachineryAndWorkingDogsPage;
    }
}
