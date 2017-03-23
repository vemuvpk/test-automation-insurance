package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.AccessoryRow;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketAccessoriesGrid;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketModificationsGrid;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;

@Activity( "Create a Farm Motor Trailer with Manual Premium in a manner that allows a policy to be offered" )
public class CreateFarmMotorTrailerWithManualPremiumActivity extends
    AbstractActivity< FarmMotorTrailerPage >
{
    private HarvestPage _page;

    public CreateFarmMotorTrailerWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailerPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();
        FarmMotorTrailerPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailer();

        vehicleDetailPage.setVehicleDescriptionDetails( "2000", "Description Vehicle", "Boat" );
        vehicleDetailPage.setVehicleIdentificationDetails( "true", "AAA-998" );
        vehicleDetailPage.getNavigationTree().containsItem( "AAA-998" );
        vehicleDetailPage.setVehicleRatingDetails( "9999", "Raedown", "NSW", "2 years",
                "true", "$65,000", "Standard Excess", "$500", "false", "1979" );
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
