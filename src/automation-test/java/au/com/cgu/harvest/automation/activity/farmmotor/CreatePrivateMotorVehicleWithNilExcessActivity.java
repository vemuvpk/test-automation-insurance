package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;

@Activity( "Create a private motor vehicle With Nil Excess in a manner that allows a policy to be completed" )
public class CreatePrivateMotorVehicleWithNilExcessActivity extends
    AbstractActivity< PrivateMotorVehiclePage >
{
    private HarvestPage _page;

    public CreatePrivateMotorVehicleWithNilExcessActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        PrivateMotorVehiclePage vehicleDetailPage =
            addVehiclePage.addPrivateMotorVehicleForNilExcess();
        vehicleDetailPage.setVehicleDetails( "AAA-999", "3350", "Ballarat", "VIC" );
        vehicleDetailPage.selectComprehensiveCover( "true", "2 years" );
        vehicleDetailPage.setObtainedProofOfClaimFreeYears( "Yes" );

        // THESE ARE ACTIVITIES - SHOULD USE TASKS HERE TO AVOID THE ASSIGNMENT
        // OF THE PAGE BACK AFTER THE RESULT
        AddADriverActivity< PrivateMotorVehiclePage > addDriverActivity =
            new AddADriverActivity< PrivateMotorVehiclePage >( vehicleDetailPage );
        vehicleDetailPage = addDriverActivity.execute();

        vehicleDetailPage.setVehicleIsFinanced( "false" );
        vehicleDetailPage.setNoUnrepairedDamage();
        vehicleDetailPage.setHasAfterMarketAccessoriesAndModifications( "false", "false" );
        vehicleDetailPage.setExcess( "Nil Excess", "500" );

        return vehicleDetailPage;
    }
}
