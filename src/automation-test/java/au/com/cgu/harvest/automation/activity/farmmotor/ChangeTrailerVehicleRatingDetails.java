package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;

@Activity( "Change Trailers Vehicle Rating Details" )
public class ChangeTrailerVehicleRatingDetails extends
    AbstractActivity< FarmMotorTrailerPage >
{
    private FarmMotorTrailerPage _page;

    public ChangeTrailerVehicleRatingDetails( FarmMotorTrailerPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailerPage execute()
    {
        _page.setVehicleRatingDetails( "2000", "Sydney", "NSW", "1 year", "true",
            "11,800", "Standard Excess", "100", "false", "1978" );
        return _page;
    }
}
