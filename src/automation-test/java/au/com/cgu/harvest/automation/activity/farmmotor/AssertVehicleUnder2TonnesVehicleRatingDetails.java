package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPage;

@Activity( "Assert the changed Vehcile Rating Details are saved and displayed" )
public class AssertVehicleUnder2TonnesVehicleRatingDetails extends
    AbstractActivity< VehicleUnder2TonnesPage >
{
    private VehicleUnder2TonnesPage _page;

    public AssertVehicleUnder2TonnesVehicleRatingDetails( VehicleUnder2TonnesPage page )
    {
        _page = page;
    }

    @Override
    protected VehicleUnder2TonnesPage execute()
    {
        VehicleUnder2TonnesPage vehicleDetailPage =
            _page.getNavigationTree().navigateToVehicleUnder2Tonnes( 1 );

        assertEquals( "2000", _page.getGaragePostcode() );
        assertEquals( "SYDNEY", _page.getGarageSuburb() );
        assertEquals( "NSW", _page.getGarageStateCode() );
        assertEquals( "$50,000", _page.getVehicleValue() );

        return vehicleDetailPage;

    }
}
