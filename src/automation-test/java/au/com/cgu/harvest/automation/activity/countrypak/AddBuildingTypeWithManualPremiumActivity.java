package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyBuildingTypePopUp;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Add BuildingType to Farm Property Activity" )
public class AddBuildingTypeWithManualPremiumActivity extends AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyPage _page;

    public AddBuildingTypeWithManualPremiumActivity( FarmPropertyPage farmPropertyPage )
    {
        _page = farmPropertyPage;
        _page.addBuildingType();
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyBuildingTypePopUp buildingTypePopUp = _page.addBuildingType();

        buildingTypePopUp.setBuildingType( "Cool Room" );
        buildingTypePopUp.setIsPropertyUnderRennovations( "No" );
        buildingTypePopUp.setConstructionType( "Brick/Concrete Floors" );
        buildingTypePopUp.setYearOfConstruction( "2010" );
        buildingTypePopUp.setReplacement( "false" );
        buildingTypePopUp.setBuildingSumInsured( "500" );
        buildingTypePopUp.setContentsSumInsured( "5000" );

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( buildingTypePopUp.BUILDING_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( buildingTypePopUp.CONTENTS_BASE_PREMIUM_LOCATOR ),
            "E020" ) );

        buildingTypePopUp.setBuildingBasePremium( "55.55" );
        buildingTypePopUp.setContentsBasePremium( "55.55" );

        // Check for R062
        wait.until( Rule.isDisplayed(
            By.xpath( buildingTypePopUp.BUILDING_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( buildingTypePopUp.CONTENTS_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        buildingTypePopUp.ok();
        return _page;
    }
}
