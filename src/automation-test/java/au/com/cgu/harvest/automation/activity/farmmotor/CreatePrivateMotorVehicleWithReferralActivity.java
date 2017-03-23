package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;

@Activity( "Create a Private motor vehicle With a referral" )
public class CreatePrivateMotorVehicleWithReferralActivity extends
    AbstractActivity< PrivateMotorVehiclePage >
{
    private PrivateMotorVehiclePage _page;

    public CreatePrivateMotorVehicleWithReferralActivity( PrivateMotorVehiclePage page )
    {
        _page = page;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        _page.selectNonComprehensiveCover( "Fire, Theft and Third Party Property Damage" );
        _page.setVehicleValue( "11000" );
        return _page;
    }

}
