package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.DriverRow;
import au.com.cgu.harvest.pages.farmmotor.IPageWithDrivers;

@Activity( "Add a Driver Row to a Vehicle" )
public class AddADriverActivity< PAGE extends IPageWithDrivers > extends
    AbstractActivity< PAGE >
{
    private PAGE _page;

    public AddADriverActivity( PAGE vehicleDetailPage )
    {
        _page = vehicleDetailPage;
    }

    @Override
    protected PAGE execute()
    {
        DriverRow driverRow = _page.addDriver();
        driverRow.setDriverName( "Paul Carruthers" );
        driverRow.setDateOfBirth( "17/04/1968" );
        driverRow.setGender( "Male" );
        driverRow.setUsualDriver();

        return _page;
    }
}
