package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.DriverRow;
import au.com.cgu.harvest.pages.GlassesGuideAccessoryRow;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.GlassesGuideAfterMarketAccessoriesGrid;
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPage;

@Activity( "Create a Vehicle Under 2 Tonnes with Manual premium in a manner that allows a policy to be offered" )
public class CreateVehicleUnder2TonnesWithManualPremiumActivity extends
    AbstractActivity< VehicleUnder2TonnesPage >
{
    private HarvestPage _page;

    public CreateVehicleUnder2TonnesWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected VehicleUnder2TonnesPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();
        VehicleUnder2TonnesPage vehicleDetailPage = addVehiclePage.addVehicleUnder2Tonnes();

        vehicleDetailPage.setVehicleIdentificationDetails( "REGO" );
        vehicleDetailPage.getNavigationTree().containsItem( "REGO" );
        vehicleDetailPage.setVehicleRatingDetails( "9999", "Raedown", "NSW", "$99,300" );
        vehicleDetailPage.setComprehensiveCover( "false", "3 years" );
        vehicleDetailPage.setObtainedProofOfClaimFreeYears( "Yes" );
        vehicleDetailPage.setExcess( "Nil Excess", "$1000" );
        vehicleDetailPage.setNoUnrepairedDamage( "false" );
        vehicleDetailPage.setDoesTheVehicleRegularlyOperateOutsideTheBase( "false" );
        vehicleDetailPage.setVehicleIsFinanced( "false" );

        DriverRow driverRow = vehicleDetailPage.getDrivers().getDriverAtRow( 1 );
        driverRow.setValues( "Vemu", "06/10/1979", "Male", true );

        // setting After Market Accessories and Modifications
        GlassesGuideAfterMarketAccessoriesGrid accessories =
            vehicleDetailPage.getAfterMarketAccessories();
        vehicleDetailPage.getAfterMarketAccessories().setHasAfterMarketAccessories( "true" );
        GlassesGuideAccessoryRow accessoryRow = accessories.getAccessoryAtRow( 1 );
        accessoryRow.setValues( "Body Kit", "Bull Bars", "100" );
        vehicleDetailPage.setHasAfterMarketModifications( "false" );

        vehicleDetailPage.setStandardExcess( "$500" );
        vehicleDetailPage.setBasePremium( "999.99" );

        return vehicleDetailPage;
    }

}
