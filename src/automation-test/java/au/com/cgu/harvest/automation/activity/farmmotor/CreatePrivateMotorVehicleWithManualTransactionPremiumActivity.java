package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;

@Activity( "Create a private motor vehicle that is Manually Rated in endorsement transaction in a manner that allows a policy to be completed" )
public class CreatePrivateMotorVehicleWithManualTransactionPremiumActivity extends
    AbstractActivity< PrivateMotorVehiclePage >
{
    private HarvestPage _page;

    public CreatePrivateMotorVehicleWithManualTransactionPremiumActivity( HarvestPage page )
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
        vehicleDetailPage.setWindScreenExtension( "false" );
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

        vehicleDetailPage.setStandardExcess( "$500" );
        vehicleDetailPage.setTransactionBasePremium( "999.99" );

        return vehicleDetailPage;
    }
}
