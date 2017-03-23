package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyBuildingTypePopUp;

@Activity( "Exercise Referral/Declined Rules for Farm Property -  Building Type Popup Activity" )
public class ExerciseReferralRulesForBuildingTypePopupActivity extends
    AbstractActivity< FarmPropertyBuildingTypePopUp >
{
    private HarvestPage _page;

    public ExerciseReferralRulesForBuildingTypePopupActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyBuildingTypePopUp execute()
    {

        FarmPropertyBuildingTypePopUp farmBuildingTypePopUp =
            _page.getNavigationTree().navigateToFarmProperty( 1 ).addBuildingType();

        farmBuildingTypePopUp.setConstructionType( "Other" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( farmBuildingTypePopUp.BUILDING_TYPE_LOCATOR ),
            "R042" ) );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.BUILDING_SUM_INSURED_LOCATOR ),
            "E008" ) );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.CONTENTS_SUM_INSURED_LOCATOR ),
            "E008" ) );

        farmBuildingTypePopUp.setIsPropertyUnderRennovations( "Undergoing Renovation > $50,000" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.IS_PROPERTY_UNDER_RENNOVATIONS_LOCATOR ), "R043" ) );

        farmBuildingTypePopUp.setBuildingType( "Cool Room" );
        farmBuildingTypePopUp.setBuildingSumInsured( "50001" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.BUILDING_SUM_INSURED_LOCATOR ),
            "R042" ) );

        farmBuildingTypePopUp.setBuildingType( "Piggery" );
        farmBuildingTypePopUp.setBuildingSumInsured( "100001" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.BUILDING_SUM_INSURED_LOCATOR ),
            "R042" ) );

        farmBuildingTypePopUp.setBuildingType( "Packing Shed" );
        farmBuildingTypePopUp.setBuildingSumInsured( "100001" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.BUILDING_SUM_INSURED_LOCATOR ),
            "R042" ) );

        farmBuildingTypePopUp.setIsPropertyUnderRennovations( "Undergoing Construction" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.IS_PROPERTY_UNDER_RENNOVATIONS_LOCATOR ), "D006" ) );

        farmBuildingTypePopUp.setIsPropertyUnderRennovations( "To Be Demolished" );
        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.IS_PROPERTY_UNDER_RENNOVATIONS_LOCATOR ), "D006" ) );

        farmBuildingTypePopUp.checkOkDisabled();

        return farmBuildingTypePopUp;
    }
}
