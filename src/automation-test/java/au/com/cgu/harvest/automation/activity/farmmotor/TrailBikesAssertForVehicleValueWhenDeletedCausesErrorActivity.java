package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.RetryingElementLocator;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPageXpath;

@Activity( "Assert For TrailBikes Vehicle Value When Deleted Causes Error Activity" )
public class TrailBikesAssertForVehicleValueWhenDeletedCausesErrorActivity
    extends
    AbstractActivity< FarmMotorTrailBikesPage >
{
    private HarvestPage _page;

    public TrailBikesAssertForVehicleValueWhenDeletedCausesErrorActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailBikesPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailBikes();

        RetryingElementLocator.getTextBox( getWebDriver(), "Vehicle Value Deleted",
            FarmMotorTrailBikesPageXpath.VEHICLE_VALUE,
            null ).clear();
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( FarmMotorTrailBikesPageXpath.VEHICLE_VALUE ) ) );

        vehicleDetailPage.setVehicleValue( "$0" );
        wait.until( Rule.isDisplayed( By.xpath( FarmMotorTrailBikesPageXpath.VEHICLE_VALUE ),
            "E021" ) );

        return vehicleDetailPage;

    }

}
