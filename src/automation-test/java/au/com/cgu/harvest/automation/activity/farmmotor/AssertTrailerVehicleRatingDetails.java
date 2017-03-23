package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;

@Activity( "Assert the changed Vehcile Rating Details are saved and displayed" )
public class AssertTrailerVehicleRatingDetails extends
    AbstractActivity< FarmMotorTrailerPage >
{
    private FarmMotorTrailerPage _page;

    public AssertTrailerVehicleRatingDetails( FarmMotorTrailerPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailerPage execute()
    {
        FarmMotorTrailerPage vehicleDetailPage =
            _page.getNavigationTree().navigateToFarmMotorTrailerGrid( 1 );
        assertEquals( "2000", _page.getGaragePostcode() );
        assertEquals( "$11,800", _page.getVehicleValue() );
        assertEquals( "$100", _page.getCompulsoryExcess() );
        assertEquals( "1978", _page.getYearOfBirthOfUsualDriver() );

        return vehicleDetailPage;

    }
}
