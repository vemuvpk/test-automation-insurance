package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;

@Activity( "Navigate to Agricultural Vehicle Page in Farm Motor" )
public class NavigateToAgriculturalVehiclePageActivity extends
    AbstractActivity< AgriculturalVehiclePage >

{
    private HarvestPage _page;

    public NavigateToAgriculturalVehiclePageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected AgriculturalVehiclePage execute()
    {
        return _page.getNavigationTree().navigateToAgriculturalVehicle( 2 );

    }
}
