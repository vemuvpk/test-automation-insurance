package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePageXpath;

@Activity( "AgriculturalVehicle Assert That IsRegistered Causes MandatoryError When Changed Back To Default Activity" )
public class AgriculturalVehicleAssertThatIsRegisteredCausesMandatoryErrorWhenChangedBackToDefaultActivity
    extends
    AbstractActivity< AgriculturalVehiclePage >
{
    private HarvestPage _page;

    public AgriculturalVehicleAssertThatIsRegisteredCausesMandatoryErrorWhenChangedBackToDefaultActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected AgriculturalVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        AgriculturalVehiclePage vehicleDetailPage = addVehiclePage.addAgriculturalMotorVehicle();

        AbstractScenario.getScenarioLogger().trace( "setting Is Registered to Yes" );
        vehicleDetailPage.setIsRegistered( "true" );
        AbstractScenario.getScenarioLogger().trace( "setting Registration Number" );
        vehicleDetailPage.setRegistrationNumber( "ABG-999" );
        AbstractScenario.getScenarioLogger().trace( "setting Is Registered to No" );
        vehicleDetailPage.setIsRegistered( "false" );

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( AgriculturalVehiclePageXpath.VEHICLE_IDENTIFICATION_NUMBER ) ) );
        return vehicleDetailPage;

    }

}
