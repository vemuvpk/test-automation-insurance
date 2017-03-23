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
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePageXpath;

@Activity( "AgriculturalVehicle Assert That RegistrationNUmber IsMandatory When Vehicle Is SetTo Registered Activity" )
public class AgriculturalVehicleAssertThatRegistrationNUmberIsMandatoryWhenVehicleIsSetToRegisteredActivity
    extends
    AbstractActivity< AgriculturalVehiclePage >
{
    private HarvestPage _page;

    public AgriculturalVehicleAssertThatRegistrationNUmberIsMandatoryWhenVehicleIsSetToRegisteredActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected AgriculturalVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        AgriculturalVehiclePage vehicleDetailPage = addVehiclePage.addAgriculturalMotorVehicle();

        vehicleDetailPage.setIsRegistered( "true" );
        vehicleDetailPage.setRegistrationNumber( "" );

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( AgriculturalVehiclePageXpath.REGISTRATION_NUMBER ) ) );
        return vehicleDetailPage;

    }

}
