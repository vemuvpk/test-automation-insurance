package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;

@Activity( "Change Agricultural Vehicle Rating Details" )
public class ChangePrivateMotorVehicleRatingDetails extends
    AbstractActivity< PrivateMotorVehiclePage >
{
    private PrivateMotorVehiclePage _page;

    public ChangePrivateMotorVehicleRatingDetails( PrivateMotorVehiclePage page )
    {
        _page = page;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        _page.setVehicleDetails( "REGO", "2131", "Ashfield", "NSW" );
        _page.setVehicleValue( "45000" );
        return _page;
    }
}
