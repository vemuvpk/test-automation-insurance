package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.DwellingPopUp;

@Activity( "Add DwellingType to Domestic Building Activity" )
public class AddDwellingTypeWithManualPremium extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DomesticBuildingAndContentsPage _page;

    public AddDwellingTypeWithManualPremium(
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage )
    {
        _page = domesticBuildingAndContentsPage;
        _page.addDwellingType();
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DwellingPopUp dwelllingPopUp = _page.addDwellingType();

        dwelllingPopUp.setDwellingType( "Dwelling" );
        dwelllingPopUp.setAdditionalDwellingDetails( "Additional Details" );
        dwelllingPopUp.setYearBuilt( "2010" );
        dwelllingPopUp.setPropertyOccupancy( "Owner Occupied" );
        dwelllingPopUp.setOccupancyStatus( "Permanently Occupied" );
        dwelllingPopUp.setConstructionType( "Brick/Concrete Floors" );
        dwelllingPopUp.setIsBuildingHistorical( "false" );
        dwelllingPopUp.setIsBuildingUnderRennovations( "No" );
        dwelllingPopUp.setBuildingSumInsured( "500" );
        dwelllingPopUp.setContentsSumInsured( "5000" );
        dwelllingPopUp.getSpecifiedContentsGrid().addRow( "Contents-1", "100" );
        dwelllingPopUp.getSpecifiedContentsGrid().addRow( "Contents-2", "200" );

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( dwelllingPopUp.BUILDING_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( dwelllingPopUp.CONTENTS_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( dwelllingPopUp.SPECIFIED_CONTENT_BASE_PREMIUM_LOCATOR ),
            "E020" ) );

        dwelllingPopUp.setBuildingBasePremium( "55.55" );
        dwelllingPopUp.setContentsBasePremium( "55.55" );
        dwelllingPopUp.setSpecifiedContentsBasePremium( "55.55" );

        // Check for R062
        wait.until( Rule.isDisplayed(
            By.xpath( dwelllingPopUp.BUILDING_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( dwelllingPopUp.CONTENTS_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( dwelllingPopUp.SPECIFIED_CONTENT_BASE_PREMIUM_LOCATOR ),
            "R062" ) );

        dwelllingPopUp.ok();
        return _page;
    }
}
