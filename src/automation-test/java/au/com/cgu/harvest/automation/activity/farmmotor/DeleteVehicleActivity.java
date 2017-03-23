package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.VehicleDetailPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;

@Activity( "Activity to delete any vehicle" )
public class DeleteVehicleActivity< PAGE extends VehicleDetailPage > extends
    AbstractActivity< VehicleSummaryPage >
{
    private PAGE _page;

    public DeleteVehicleActivity( PAGE vehicleDetailPage )
    {
        _page = vehicleDetailPage;
    }

    @Override
    protected VehicleSummaryPage execute()
    {
        return _page.deleteVehicle();
    }
}
