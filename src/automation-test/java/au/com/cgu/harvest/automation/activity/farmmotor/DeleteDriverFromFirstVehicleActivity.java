package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.DriverRow;
import au.com.cgu.harvest.pages.farmmotor.IPageWithDrivers;

@Activity( "Delete Driver added in Previous Vehicle" )
public class DeleteDriverFromFirstVehicleActivity< PAGE extends IPageWithDrivers > extends
    AbstractActivity< PAGE >
{
    private PAGE _page;

    public DeleteDriverFromFirstVehicleActivity( PAGE vehicleDetailPage )
    {
        _page = vehicleDetailPage;
    }

    @Override
    protected PAGE execute()
    {
        DriverRow driverRow = _page.getDrivers().getDriverAtRow( 1 );
        driverRow.hasValues( false, "Paul Carruthers", "17/04/1968", "Male", false );
        driverRow = _page.getDrivers().getDriverAtRow( 3 );
        driverRow.hasValues( true, "Paul Carruthers", "17/04/1968", "Male", true );
        driverRow = _page.getDrivers().getDriverAtRow( 1 );
        driverRow.delete( true );
        AbstractScenario.getScenarioLogger().trace(
            "Driver from previous vehicle deleted successfully" );

        return _page;
    }
}
