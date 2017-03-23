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
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPageXpath;

@Activity( "Assert For TrailBikes IsRegistered Causes Error When Changed BackTo Default Activity" )
public class TrailBikesAssertForIsRegisteredCausesErrorWhenChangedBackToDefaultActivity
    extends
    AbstractActivity< FarmMotorTrailBikesPage >
{
    private HarvestPage _page;

    public TrailBikesAssertForIsRegisteredCausesErrorWhenChangedBackToDefaultActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailBikesPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailBikes();

        // Assert That "IsRegistered" Causes Error When ChangedBackTo Default
        AbstractScenario.getScenarioLogger().trace( "setting Is Registered to Yes" );
        vehicleDetailPage.setIsRegistered( "true" );
        AbstractScenario.getScenarioLogger().trace( "setting Registration Number" );
        vehicleDetailPage.setRegistrationNumber( "ABG-999" );
        vehicleDetailPage.setIsRegistered( "false" );

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( FarmMotorTrailBikesPageXpath.VEHICLE_IDENTIFICATION_NUMBER ) ) );

        return vehicleDetailPage;

    }

}
