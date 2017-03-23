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

@Activity( "Assert For Registration Number Is Mandatory When IsRegistered Set To Yes Activity" )
public class TrailBikesAssertForRegistrationNumberIsMandatoryWhenIsRegisteredSetToYesActivity
    extends
    AbstractActivity< FarmMotorTrailBikesPage >
{
    private HarvestPage _page;

    public TrailBikesAssertForRegistrationNumberIsMandatoryWhenIsRegisteredSetToYesActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailBikesPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailBikes();

        // assertThatRegistrationNumberIsMandatoryWhenIsRegistered()
        vehicleDetailPage.setIsRegistered( "true" );
        vehicleDetailPage.setRegistrationNumber( "" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( FarmMotorTrailBikesPageXpath.REGISTRATION_NUMBER ) ) );

        return vehicleDetailPage;

    }

}
