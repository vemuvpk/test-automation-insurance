package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;

@Activity( "Assert the changed Vehcile Rating Details are saved and displayed" )
public class AssertTrailBikesVehicleRatingDetails extends
    AbstractActivity< FarmMotorTrailBikesPage >
{
    private FarmMotorTrailBikesPage _page;

    public AssertTrailBikesVehicleRatingDetails( FarmMotorTrailBikesPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        FarmMotorTrailBikesPage vehicleDetailPage =
            _page.getNavigationTree().navigateToFarmMotorTrailBikes( 1 );
        assertEquals( "Comprehensive", _page.getTypeOfCover() );
        assertEquals( "2000", _page.getGaragePostcode() );
        assertEquals( "$11,800", _page.getVehicleValue() );
        assertEquals( "1978", _page.getYearOfBirthOfUsualDriver() );

        return vehicleDetailPage;

    }
}
