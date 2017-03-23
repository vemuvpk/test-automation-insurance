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
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPageXpath;

@Activity( "FarmMotorTrailers Assert For IsRegistered Causes Mandatory Error When Changed Back To Default Activity" )
public class FarmMotorTrailersAssertForIsRegisteredCausesMandatoryErrorWhenChangedBackToDefaultActivity
    extends
    AbstractActivity< FarmMotorTrailerPage >
{
    private HarvestPage _page;

    public FarmMotorTrailersAssertForIsRegisteredCausesMandatoryErrorWhenChangedBackToDefaultActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailerPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailerPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailer();

        // assertThatIsRegisteredCausesErrorWhenChangedBackToDefault()
        AbstractScenario.getScenarioLogger().trace( "setting Is Registered to Yes" );
        vehicleDetailPage.setIsRegistered( "true" );
        AbstractScenario.getScenarioLogger().trace( "setting Registration Number" );
        vehicleDetailPage.setRegistrationNumber( "ABG-999" );
        vehicleDetailPage.setIsRegistered( "" );

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( FarmMotorTrailerPageXpath.IS_VEHICLE_REGISTERED ) ) );

        return vehicleDetailPage;

    }

}
