package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add farm Property Activity" )
public class AddFarmPropertyWithManualPremiumActivity extends AbstractActivity< FarmPropertyPage >
{
    private HarvestPage _page;

    public AddFarmPropertyWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyPage farmPropertyPage =
            _page.getNavigationTree().navigateToFarmProperty( 1 );

        farmPropertyPage.setCoverType( "Listed Events" );
        farmPropertyPage.setExcess( "500.000" );
        farmPropertyPage.setUnspecifiedFarmBuildings( "100" );
        farmPropertyPage.setAllFarmContentsLimit( "500" );
        farmPropertyPage.setInAnyOneBuilding( "500" );
        farmPropertyPage.setAboveGroundFarmImprovements( "5000" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.FARM_PROPERTY, 1 );
        section.isTaken();
        farmPropertyPage.getSpecifiedAboveGroundFarmImprovementsGrid().addRow( "Description-1",
            "5000" );
        farmPropertyPage.getSpecifiedAboveGroundFarmImprovementsGrid().addRow( "Description-2",
            "5000" );

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.UNSPECIFIED_FARM_BUILDING_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.IN_ANY_ONE_BUILDING_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.ABOVE_GROUND_FARM_CONTENTS_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        farmPropertyPage.setAboveGroundFarmImprovementsBasePremium( "55.55" );
        farmPropertyPage.setUnspecifiedFarmBuildingBasePremium( "55.55" );
        farmPropertyPage.setInAnyOneBuildingBasePremium( "55.55" );
        farmPropertyPage.setAboveGroundFarmImprovementsBasePremium( "55.55" );

        // Check for R036
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.UNSPECIFIED_FARM_BUILDING_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.IN_ANY_ONE_BUILDING_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.ABOVE_GROUND_FARM_CONTENTS_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        farmPropertyPage.setSpecifiedFarmImprovementsBasePremium( "55.55" );
        return farmPropertyPage;
    }
}
