package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPageXpath;

@Activity( "Assert TrailBikes VIN IsMandatory When Vehicle IsRegistered Activity" )
public class TrailBikesAssertForVINIsMandatoryWhenVehicleIsRegisteredActivity
    extends
    AbstractActivity< FarmMotorTrailBikesPage >
{
    private HarvestPage _page;

    public TrailBikesAssertForVINIsMandatoryWhenVehicleIsRegisteredActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailBikesPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailBikes();

        // assertVinIsMandatoryWhenIsRegistered()
        vehicleDetailPage.setIsRegistered( "false" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( FarmMotorTrailBikesPageXpath.VEHICLE_IDENTIFICATION_NUMBER ) ) );

        return vehicleDetailPage;

    }

}
