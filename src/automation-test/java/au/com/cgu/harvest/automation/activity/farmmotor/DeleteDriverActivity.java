package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.DriverRow;
import au.com.cgu.harvest.pages.farmmotor.IPageWithDrivers;

@Activity( "Delete a Driver Row from a Vehicle" )
public class DeleteDriverActivity< PAGE extends IPageWithDrivers > extends
    AbstractActivity< PAGE >
{
    private PAGE _page;

    public DeleteDriverActivity( PAGE vehicleDetailPage )
    {
        _page = vehicleDetailPage;
    }

    @Override
    protected PAGE execute()
    {
        DriverRow driverRow = _page.getDrivers().getDriverAtRow( 1 );
        driverRow.delete( true );

        return _page;
    }

}
