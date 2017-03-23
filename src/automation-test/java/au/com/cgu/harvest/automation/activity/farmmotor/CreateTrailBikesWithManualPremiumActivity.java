package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.AccessoryRow;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketAccessoriesGrid;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketModificationsGrid;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;

@Activity( "Create a Trail Bike With Manual Premium in a manner that allows a policy to be offered" )
public class CreateTrailBikesWithManualPremiumActivity extends
    AbstractActivity< FarmMotorTrailBikesPage >
{
    private HarvestPage _page;

    public CreateTrailBikesWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();
        FarmMotorTrailBikesPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailBikes();

        vehicleDetailPage.setVehicleIdentificationDetails( "true", "VE06MU", "VINFINISH" );
        vehicleDetailPage.setVehicleRatingDetails( "9999", "Raedown", "NSW", "Comprehensive",
            "2 years", "true",
            "$6,000", "Standard Excess", "1979" );

        vehicleDetailPage.setInterestedParty( "false" );
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
