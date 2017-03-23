package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePageXpath;

@Activity( "Create a private motor vehicle that is Manually Rated And check E020 and R036 are triggered" )
public class CreateManuallyRatedPMVToCheckRulesActivity extends
    AbstractActivity< PrivateMotorVehiclePage >
{
    private HarvestPage _page;

    public CreateManuallyRatedPMVToCheckRulesActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        PrivateMotorVehiclePage vehicleDetailPage = addVehiclePage.addPrivateMotorVehicle();
        vehicleDetailPage.setVehicleDetails( "MANUAL", "9999", "Raedown", "NSW" );
        vehicleDetailPage.setTypeOfCover( "Comprehensive" );
        vehicleDetailPage.setWindScreenExtension( "true" );
        vehicleDetailPage.setAtFaultClaimFreeYears( "2 years" );
        vehicleDetailPage.setObtainedProofOfClaimFreeYears( "true" );

        // THESE ARE ACTIVITIES - SHOULD USE TASKS HERE TO AVOID THE ASSIGNMENT
        // OF THE PAGE BACK AFTER THE RESULT
        AddADriverActivity< PrivateMotorVehiclePage > addDriverActivity =
            new AddADriverActivity< PrivateMotorVehiclePage >( vehicleDetailPage );
        vehicleDetailPage = addDriverActivity.execute();

        vehicleDetailPage.setVehicleIsFinanced( "false" );
        vehicleDetailPage.setNoUnrepairedDamage();
        vehicleDetailPage.setHasAfterMarketAccessoriesAndModifications( "false", "false" );
        // Check Rule E020
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( PrivateMotorVehiclePageXpath.STANDARD_EXCESS ),
            "E020" ) );

        wait.until( Rule.isDisplayed( By.xpath( PrivateMotorVehiclePageXpath.BASE_PREMIUM ),
            "E020" ) );

        wait.until( Rule.isDisplayed(
            By.xpath( PrivateMotorVehiclePageXpath.WINDSCREEN_PREMIUM ),
            "E020" ) );

        vehicleDetailPage.setStandardExcess( "$500" );
        vehicleDetailPage.setBasePremium( "999.99" );
        vehicleDetailPage.setWindScreenPremium( "55.55" );

        // Check for R036
        wait.until( Rule.isDisplayed( By.xpath( PrivateMotorVehiclePageXpath.BASE_PREMIUM ),
            "R036" ) );
        return vehicleDetailPage;
    }
}
