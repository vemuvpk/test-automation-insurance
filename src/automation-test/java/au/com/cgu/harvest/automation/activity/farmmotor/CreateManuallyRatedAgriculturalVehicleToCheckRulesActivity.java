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
import au.com.cgu.harvest.pages.farmmotor.AfterMarketAccessoriesGrid;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketModificationsGrid;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePageXpath;

@Activity( "Create a Agricultural Motor Vehicle with manual Premium in a manner that allows a policy to be offered" )
public class CreateManuallyRatedAgriculturalVehicleToCheckRulesActivity extends
    AbstractActivity< AgriculturalVehiclePage >
{
    private HarvestPage _page;

    public CreateManuallyRatedAgriculturalVehicleToCheckRulesActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected AgriculturalVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        AgriculturalVehiclePage vehicleDetailPage = addVehiclePage.addAgriculturalMotorVehicle();
        vehicleDetailPage.setVehicleIdentificationDetails( "true", "AAA-998", "FinishVehicle" );
        vehicleDetailPage.setVehicleDescriptionDetails( "Caterpillar", "XSD", "2000", "Harvester" );
        vehicleDetailPage.setSuburbStatePostcode( "Raedown", "NSW", "9999" );
        vehicleDetailPage.setVehicleValue( "65000" );
        vehicleDetailPage.setTypeOfCover( "Comprehensive" );
        vehicleDetailPage.setLimitOfLiability( "30000000.000" );
        vehicleDetailPage.setYearOfBirthOfUsualDriver( "1981" );
        vehicleDetailPage.setGSTInclusive( "true" );
        vehicleDetailPage.setExistingUnrepairedDamage( "false" );
        vehicleDetailPage.setMachineDetails( "Bobcat", "Own Use" );
        vehicleDetailPage.setVehicleIsFinanced( "false" );
        // setting After Market Accessories and Modifications
        AfterMarketAccessoriesGrid accessories = vehicleDetailPage.getAfterMarketAccessories();
        accessories.setHasAfterMarketAccessories( "false" );
        AfterMarketModificationsGrid modifications =
            vehicleDetailPage.getAfterMarketModifications();
        modifications.setHasAfterMarketModifications( "false" );

        // Check Rule E020
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( AgriculturalVehiclePageXpath.STANDARD_EXCESS ),
            "E020" ) );

        wait.until( Rule.isDisplayed(
            By.xpath( AgriculturalVehiclePageXpath.BASE_PREMIUM ),
            "E020" ) );

        vehicleDetailPage.setStandardExcess( "$500" );
        vehicleDetailPage.setBasePremium( "999.99" );

        wait.until( Rule.isDisplayed( By.xpath( AgriculturalVehiclePageXpath.BASE_PREMIUM ),
            "R036" ) );
        return vehicleDetailPage;
    }

}
