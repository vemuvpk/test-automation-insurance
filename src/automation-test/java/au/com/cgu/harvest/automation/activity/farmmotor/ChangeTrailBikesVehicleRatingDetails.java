package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;

@Activity( "Change Trail Bikes Vehicle Rating Details" )
public class ChangeTrailBikesVehicleRatingDetails extends
    AbstractActivity< FarmMotorTrailBikesPage >
{
    private FarmMotorTrailBikesPage _page;

    public ChangeTrailBikesVehicleRatingDetails( FarmMotorTrailBikesPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        _page.setVehicleRatingDetails( "2000", "Sydney", "NSW", "Comprehensive", "1 year", "true",
            "11,800", "Standard Excess", "1978" );
        return _page;
    }
}
