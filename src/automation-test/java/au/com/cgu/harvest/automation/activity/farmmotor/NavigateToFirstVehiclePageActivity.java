package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;

@Activity( "Navigate to Vehicle page in Fram Motor" )
public class NavigateToFirstVehiclePageActivity extends AbstractActivity< PrivateMotorVehiclePage >

{
    private HarvestPage _page;

    public NavigateToFirstVehiclePageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        return _page.getNavigationTree().navigateToPrivateMotorVehicle( 1 );

    }
}
