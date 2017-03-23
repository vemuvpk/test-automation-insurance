package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.countrypak.DwellingPopUp;

@Activity( "Exercise Mandatory Rules for Dwelling Type Popup Activity" )
public class ExerciseMandatoryRulesForDwellingTypePopupActivity extends
    AbstractActivity< DwellingPopUp >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForDwellingTypePopupActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected DwellingPopUp execute()
    {

        DwellingPopUp dwellingPopUp =
            _page.getNavigationTree().navigateToDomesticBuilding( 1 ).addDwellingType();

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError
            .isDisplayed( By.xpath( dwellingPopUp.DWELLING_TYPE_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By.xpath( dwellingPopUp.YEAR_BUILT_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.PROPERTY_OCCUPANCY_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.OCCUPANCY_STATUS_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.CONSTRUCTION_TYPE_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.IS_BUILDING_HISTORICAL_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.IS_PROPERTY_UNDER_RENNOVATION_LOCATOR ) ) );

        dwellingPopUp.setConstructionType( "Other" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.DESCRIPTION_OF_CONSTRUCTION_TYPE_LOCATOR ) ) );

        dwellingPopUp.setConstructionType( "Inferior" );
        dwellingPopUp.setBuildingSumInsured( "250001" );
        dwellingPopUp.setYearBuilt( "1800" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.HAS_PROPERTY_BEEN_REWIRED_LOCATOR ) ) );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.IS_ROOF_IN_GOOD_CONDITION_LOCATOR ) ) );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.IS_PROPERTY_WELL_MAINTAINED_LOCATOR ) ) );

        dwellingPopUp.setConstructionType( "Brick/Concrete Floors" );
        dwellingPopUp.setBuildingSumInsured( "350001" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.HAS_PROPERTY_BEEN_REWIRED_LOCATOR ) ) );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.IS_ROOF_IN_GOOD_CONDITION_LOCATOR ) ) );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( dwellingPopUp.IS_PROPERTY_WELL_MAINTAINED_LOCATOR ) ) );

        AbstractScenario.getScenarioLogger().trace(
            "Finished checking for Mandatory fields in Dwelling Type" );

        dwellingPopUp.checkOkDisabled();

        return dwellingPopUp;
    }
}
