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
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.DwellingPopUp;

@Activity( "Exercise Referral/Declined Rules for Dwelling Type Popup Activity" )
public class ExerciseReferralRulesForDwellingTypePopupActivity extends
    AbstractActivity< DwellingPopUp >
{
    private HarvestPage _page;

    public ExerciseReferralRulesForDwellingTypePopupActivity( HarvestPage page )
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
        wait.until( MandatoryFieldError.isDisplayed( By.xpath(
            dwellingPopUp.DWELLING_TYPE_LOCATOR ) ) );

        dwellingPopUp.setConstructionType( "Other" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( dwellingPopUp.CONSTRUCTION_TYPE_LOCATOR ), "R045" ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By
            .xpath( dwellingPopUp.BUILDING_SUM_INSURED_LOCATOR ), "E008" ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By
            .xpath( dwellingPopUp.CONTENTS_SUM_INSURED_LOCATOR ), "E008" ) );

        dwellingPopUp.setPropertyOccupancy( "Guesthouse / Farmstay" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( dwellingPopUp.PROPERTY_OCCUPANCY_LOCATOR ), "R044" ) );

        dwellingPopUp.setPropertyOccupancy( "Holiday Home" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( dwellingPopUp.PROPERTY_OCCUPANCY_LOCATOR ), "R044" ) );

        dwellingPopUp.setOccupancyStatus( "Temporarily Unoccupied >= 90 Days" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( dwellingPopUp.OCCUPANCY_STATUS_LOCATOR ), "R044" ) );

        dwellingPopUp.setOccupancyStatus( "Permanently Unoccupied" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( dwellingPopUp.OCCUPANCY_STATUS_LOCATOR ), "R044" ) );

        dwellingPopUp.setIsBuildingUnderRennovations( "Undergoing Renovation > $50,000" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( dwellingPopUp.IS_PROPERTY_UNDER_RENNOVATION_LOCATOR ), "R043" ) );

        dwellingPopUp.setIsBuildingUnderRennovations( "Undergoing Construction" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( dwellingPopUp.IS_PROPERTY_UNDER_RENNOVATION_LOCATOR ), "D006" ) );

        dwellingPopUp.setIsBuildingUnderRennovations( "To Be Demolished" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( dwellingPopUp.IS_PROPERTY_UNDER_RENNOVATION_LOCATOR ), "D006" ) );

        AbstractScenario.getScenarioLogger().trace(
            "Finished checking for Referral/Declined Rules in Dwelling Type" );

        dwellingPopUp.checkOkDisabled();

        return dwellingPopUp;
    }
}
