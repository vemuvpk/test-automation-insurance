package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPage;

@Activity( "Change Vehicle Under 2 Tonnes Vehicle Rating Details" )
public class ChangeVehicleUnder2TonnesVehicleRatingDetails extends
    AbstractActivity< VehicleUnder2TonnesPage >
{
    private VehicleUnder2TonnesPage _page;

    public ChangeVehicleUnder2TonnesVehicleRatingDetails( VehicleUnder2TonnesPage page )
    {
        _page = page;
    }

    @Override
    protected VehicleUnder2TonnesPage execute()
    {
        _page.setVehicleRatingDetails( "2000", "Sydney", "NSW", "11,800" );
        _page.setRegistrationNumber( "1111" );
        _page.setVehicleValue( "Comprehensive", "50000", "" );

        return _page;
    }
}
