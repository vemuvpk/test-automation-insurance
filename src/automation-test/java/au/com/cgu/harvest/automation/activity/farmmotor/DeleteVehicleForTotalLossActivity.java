package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.VehicleDetailPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;

@Activity( "Activity to delete any vehicle for Total Loss" )
public class DeleteVehicleForTotalLossActivity< PAGE extends VehicleDetailPage > extends
    AbstractActivity< VehicleSummaryPage >
{
    private PAGE _page;

    public DeleteVehicleForTotalLossActivity( PAGE vehicleDetailPage )
    {
        _page = vehicleDetailPage;
    }

    @Override
    protected VehicleSummaryPage execute()
    {
        return _page.deleteVehicleForTotalLoss();
    }
}
