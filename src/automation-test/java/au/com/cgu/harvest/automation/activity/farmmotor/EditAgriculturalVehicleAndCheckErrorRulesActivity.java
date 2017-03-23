package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePageXpath;

@Activity( "Edit an existing Agricultural motor vehicle" )
public class EditAgriculturalVehicleAndCheckErrorRulesActivity extends
    AbstractActivity< AgriculturalVehiclePage >
{
    private HarvestPage _page;
    private int _positionInTree;

    public EditAgriculturalVehicleAndCheckErrorRulesActivity( HarvestPage page, int positionInTree )
    {
        _page = page;
        _positionInTree = positionInTree;
    }

    @Override
    protected AgriculturalVehiclePage execute()
    {
        AgriculturalVehiclePage vehiclePage =
            _page.getNavigationTree().navigateToAgriculturalVehicle( _positionInTree );
        vehiclePage.setYearOfManufacture( "1900" );
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( AgriculturalVehiclePageXpath.YEAR_OF_MANUFACTURE ),
            "R015" ) );
        vehiclePage.setVehicleValue( "65000000" );
        wait.until( Rule.isDisplayed(
            By.xpath( AgriculturalVehiclePageXpath.VEHICLE_VALUE ), "R076" ) );

        vehiclePage.setIsRegistered( "false" );
        vehiclePage.setIsRegistered( "true" );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( AgriculturalVehiclePageXpath.REGISTRATION_NUMBER ) ) );

        return vehiclePage;
    }
}
