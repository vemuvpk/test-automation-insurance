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
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePageXpath;

@Activity( "HeavyCommercialVehicle Assert RegistrationNumber Is Mandatory When Vehicle IsSet To Registered Activity" )
public class HeavyCommercialVehicleAssertRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity
    extends
    AbstractActivity< HeavyCommercialVehiclePage >
{
    private HarvestPage _page;

    public HeavyCommercialVehicleAssertRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HeavyCommercialVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        HeavyCommercialVehiclePage vehicleDetailPage = addVehiclePage.addVehicleOver2Tonnes();

        vehicleDetailPage.setIsRegistered( "true" );
        vehicleDetailPage.setRegistrationNumber( "" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( HeavyCommercialVehiclePageXpath.REGISTRATION_NUMBER ) ) );
        return vehicleDetailPage;

    }

}
