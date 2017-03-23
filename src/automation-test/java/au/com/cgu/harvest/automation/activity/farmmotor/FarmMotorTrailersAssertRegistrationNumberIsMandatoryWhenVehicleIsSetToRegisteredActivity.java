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
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPageXpath;

@Activity( "FarmMotorTrailers Assert Registration Number Is Mandatory When Vehicle Is Set To Registered Activity" )
public class FarmMotorTrailersAssertRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity
    extends
    AbstractActivity< FarmMotorTrailerPage >
{
    private HarvestPage _page;

    public FarmMotorTrailersAssertRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailerPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailerPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailer();

        vehicleDetailPage.setIsRegistered( "true" );
        vehicleDetailPage.setRegistrationNumber( "" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( FarmMotorTrailerPageXpath.REGISTRATION_NUMBER ) ) );

        return vehicleDetailPage;

    }

}
