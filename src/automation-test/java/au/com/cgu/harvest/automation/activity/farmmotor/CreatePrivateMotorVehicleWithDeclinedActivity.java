package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;

@Activity( "Create a Private motor vehicle With a Declined Activity" )
public class CreatePrivateMotorVehicleWithDeclinedActivity extends
    AbstractActivity< PrivateMotorVehiclePage >
{
    private PrivateMotorVehiclePage _page;

    public CreatePrivateMotorVehicleWithDeclinedActivity( PrivateMotorVehiclePage page )
    {
        _page = page;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        _page.selectComprehensiveCover( "true", "2 years" );
        _page.setVehicleValue( "1,000,000" );
        return _page;
    }
}
