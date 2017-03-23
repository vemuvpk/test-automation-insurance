package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.GlassesGuideAccessoryRow;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.GlassesGuideAfterMarketAccessoriesGrid;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePage;

@Activity( "Create a Heavy Commercial motor vehicle with Manual Premium in a manner that allows a policy to be offered" )
public class CreateHeavyCommercialVehicleWithManualPremiumActivity extends
    AbstractActivity< HeavyCommercialVehiclePage >
{
    private HarvestPage _page;

    public CreateHeavyCommercialVehicleWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HeavyCommercialVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        HeavyCommercialVehiclePage vehicleDetailPage = addVehiclePage.addVehicleOver2Tonnes();

        vehicleDetailPage.isRegistered( "true" );
        vehicleDetailPage.setRegistrationNumber( "REGO" );
        vehicleDetailPage.setSuburbStatePostcode( "Raedown", "NSW", "9999" );
        vehicleDetailPage.setTypeOfCover( "Comprehensive" );
        vehicleDetailPage.setClaimFreeYears( "4 years" );
        vehicleDetailPage.setProofForFaultClaimFreeYear( "true" );
        vehicleDetailPage.setVehicleExistingUnrepairedDamage( "false" );
        vehicleDetailPage.setVehicleOperateFromBaseOfOperation( "false" );
        vehicleDetailPage.setYearOfBirthOfUsualDriver( "1981" );
        vehicleDetailPage.setVehicleIsFinanced( "false" );

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
