package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.AccessoryRow;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketAccessoriesGrid;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketModificationsGrid;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;

@Activity( "Create a Agricultural Motor Vehicle with manual Premium in a manner that allows a policy to be offered" )
public class CreateAgriculturalVehicleWithManualPremiumActivity extends
    AbstractActivity< AgriculturalVehiclePage >
{
    private HarvestPage _page;

    public CreateAgriculturalVehicleWithManualPremiumActivity( HarvestPage page )
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
        vehicleDetailPage.setVehicleRatingDetails( "9999", "Raedown", "NSW",
            "$65,000", "Comprehensive", "30000000.000", "Standard Excess", "$500", "1970" );
        vehicleDetailPage.setGSTInclusive( "true" );
        vehicleDetailPage.setExistingUnrepairedDamage( "false" );
        vehicleDetailPage.setMachineDetails( "Bobcat", "Own Use" );
        vehicleDetailPage.setVehicleIsFinanced( "false" );
        // setting After Market Accessories and Modifications
        AfterMarketAccessoriesGrid accessories = vehicleDetailPage.getAfterMarketAccessories();
        accessories.setHasAfterMarketAccessories( "true" );
        AccessoryRow accessoryRow = accessories.getAccessoryAtRow( 1 );
        accessoryRow.setValues( "Accessory Name", "500" );
        AfterMarketModificationsGrid modifications =
            vehicleDetailPage.getAfterMarketModifications();
        modifications.setHasAfterMarketModifications( "false" );

        vehicleDetailPage.setStandardExcess( "$500" );
        vehicleDetailPage.setBasePremium( "999.99" );
        return vehicleDetailPage;
    }

}
