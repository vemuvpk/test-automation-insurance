package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;

@Activity( "Create a private motor vehicle in a manner that allows a policy to be completed" )
public class CreatePrivateMotorVehicleActivity extends AbstractActivity< PrivateMotorVehiclePage >
{
    private HarvestPage _page;

    public CreatePrivateMotorVehicleActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        PrivateMotorVehiclePage vehicleDetailPage = addVehiclePage.addPrivateMotorVehicle();
        vehicleDetailPage.setVehicleDetails( "AAA-999", "2866", "Molong", "NSW" );
        vehicleDetailPage.selectComprehensiveCover( "true", "2 years" );
        vehicleDetailPage.setObtainedProofOfClaimFreeYears( "Yes" );
        vehicleDetailPage.setExcess( "Standard Excess", "$500" );

        // THESE ARE ACTIVITIES - SHOULD USE TASKS HERE TO AVOID THE ASSIGNMENT
        // OF THE PAGE BACK AFTER THE RESULT
        AddADriverActivity< PrivateMotorVehiclePage > addDriverActivity =
            new AddADriverActivity< PrivateMotorVehiclePage >( vehicleDetailPage );
        vehicleDetailPage = addDriverActivity.execute();

        vehicleDetailPage.setVehicleIsFinanced( "false" );
        vehicleDetailPage.setNoUnrepairedDamage();
        vehicleDetailPage.setHasAfterMarketAccessoriesAndModifications( "false", "false" );

        return vehicleDetailPage;
    }
}
