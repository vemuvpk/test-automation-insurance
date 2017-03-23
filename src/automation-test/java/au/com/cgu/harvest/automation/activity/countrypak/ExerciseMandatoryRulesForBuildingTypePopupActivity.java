package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyBuildingTypePopUp;

@Activity( "Exercise Mandatory Rules for farm property-Building Type Popup Activity" )
public class ExerciseMandatoryRulesForBuildingTypePopupActivity extends
    AbstractActivity< FarmPropertyBuildingTypePopUp >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForBuildingTypePopupActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyBuildingTypePopUp execute()
    {

        FarmPropertyBuildingTypePopUp farmBuildingTypePopUp =
            _page.getNavigationTree().navigateToFarmProperty( 1 ).addBuildingType();

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( farmBuildingTypePopUp.BUILDING_TYPE_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( farmBuildingTypePopUp.IS_PROPERTY_UNDER_RENNOVATIONS_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( farmBuildingTypePopUp.CONSTRUCTION_TYPE_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( farmBuildingTypePopUp.REPLACEMENT_LOCATOR ) ) );

        farmBuildingTypePopUp.setBuildingType( "Other" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( farmBuildingTypePopUp.DESCRIPTION_OF_BUILDING_CONSTRUCTION_LOCATOR ) ) );

        farmBuildingTypePopUp.checkOkDisabled();

        return farmBuildingTypePopUp;
    }
}
