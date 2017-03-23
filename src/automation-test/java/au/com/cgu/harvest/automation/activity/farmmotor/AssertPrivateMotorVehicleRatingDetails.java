package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;

@Activity( "Assert the changed Vehcile Rating Details are saved and displayed" )
public class AssertPrivateMotorVehicleRatingDetails extends
    AbstractActivity< PrivateMotorVehiclePage >
{
    private PrivateMotorVehiclePage _page;

    public AssertPrivateMotorVehicleRatingDetails( PrivateMotorVehiclePage page )
    {
        _page = page;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        PrivateMotorVehiclePage vehicleDetailPage =
            _page.getNavigationTree().navigateToPrivateMotorVehicle( 1 );

        assertEquals( "2131", _page.getGaragePostcode() );
        assertEquals( "ASHFIELD", _page.getGarageSuburb() );
        assertEquals( "NSW", _page.getGarageStateCode() );
        assertEquals( "REGO", _page.getRegistrationNumber() );
        assertEquals( "$45,000", _page.getVehicleValue() );

        return vehicleDetailPage;
    }
}
