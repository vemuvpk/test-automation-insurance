package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPageXpath;

@Activity( "VehicleUnder2Tonnes Assert That RegistrationNumber Is Mandatory When Vehicle Is Set to Registered Activity" )
public class VehicleUnder2TonnesAssertThatRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity
    extends
    AbstractActivity< VehicleUnder2TonnesPage >
{
    private HarvestPage _page;

    public VehicleUnder2TonnesAssertThatRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected VehicleUnder2TonnesPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        VehicleUnder2TonnesPage vehicleDetailPage = addVehiclePage.addVehicleUnder2Tonnes();
        vehicleDetailPage.setRegistrationNumber( "" );
        _page.mandatoryErrorDisplayedFor( VehicleUnder2TonnesPageXpath.REGISTRATION_NUMBER );
        return vehicleDetailPage;
    }
}
