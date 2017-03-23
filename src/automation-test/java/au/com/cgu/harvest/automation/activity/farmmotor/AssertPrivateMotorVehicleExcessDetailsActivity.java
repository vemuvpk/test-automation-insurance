package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;

@Activity( "Assert the changed Vehcile Rating Details are saved and displayed" )
public class AssertPrivateMotorVehicleExcessDetailsActivity extends
    AbstractActivity< PrivateMotorVehiclePage >
{
    private PrivateMotorVehiclePage _page;

    public AssertPrivateMotorVehicleExcessDetailsActivity( PrivateMotorVehiclePage page )
    {
        _page = page;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        PrivateMotorVehiclePage vehicleDetailPage =
            _page.getNavigationTree().navigateToPrivateMotorVehicle( 1 );

        assertEquals( "3350", _page.getGaragePostcode() );
        assertEquals( "BALLARAT", _page.getGarageSuburb() );
        assertEquals( "VIC", _page.getGarageStateCode() );
        assertEquals( "Nil Excess", _page.getExcessToApply() );
        assertEquals( "$0", _page.getStandardExcess() );

        return vehicleDetailPage;

    }
}
