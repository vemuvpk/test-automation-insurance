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
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePageXpath;

@Activity( "Exercise Mandatory Rules for PMV activity" )
public class ExerciseMandatoryRulesForPMVActivity extends
    AbstractActivity< PrivateMotorVehiclePage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForPMVActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        PrivateMotorVehiclePage vehicleDetailPage = addVehiclePage.addPrivateMotorVehicle();
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( PrivateMotorVehiclePageXpath.REGISTRATION_NUMBER ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( PrivateMotorVehiclePageXpath.TYPE_OF_COVER ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( PrivateMotorVehiclePageXpath.EXISTING_UNREPAIRED_DAMAGE ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( PrivateMotorVehiclePageXpath.VEHICLE_UNDER_FINANCE ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( PrivateMotorVehiclePageXpath.AFTERMARKET_ACCESSORIES ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( PrivateMotorVehiclePageXpath.VEHICLE_MODIFICATIONS ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( "//table/tbody/tr[1]/td[2]/div/input" ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( "//table/tbody/tr[1]/td[3]/div/input" ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( "//table/tbody/tr[1]/td[4]/div//select" ) ) );

        return vehicleDetailPage;

    }
}
