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
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPageXpath;

@Activity( "FarmMotorTrailers Assert Garaged PostCode Causes Mandatory Error When Deleted Activity" )
public class FarmMotorTrailersAssertGaragedPostCodeCausesMandatoryErrorWhenDeletedActivity
    extends
    AbstractActivity< FarmMotorTrailerPage >
{
    private HarvestPage _page;

    public FarmMotorTrailersAssertGaragedPostCodeCausesMandatoryErrorWhenDeletedActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailerPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailerPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailer();

        // assertgaragePostCodecausesErrorWhenDeleted()
        RetryingElementLocator.getTextBox( getWebDriver(), "Garage PostCode",
            FarmMotorTrailerPageXpath.GARAGE_POSTCODE,
            null ).clear();
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( FarmMotorTrailerPageXpath.GARAGE_POSTCODE ) ) );

        return vehicleDetailPage;

    }

}
