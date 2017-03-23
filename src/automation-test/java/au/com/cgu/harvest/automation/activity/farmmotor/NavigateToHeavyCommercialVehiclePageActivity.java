package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePage;

@Activity( "Navigate to Heavy Commercial Vehicle page in Farm Motor" )
public class NavigateToHeavyCommercialVehiclePageActivity extends
    AbstractActivity< HeavyCommercialVehiclePage >

{
    private HarvestPage _page;

    public NavigateToHeavyCommercialVehiclePageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HeavyCommercialVehiclePage execute()
    {
        return _page.getNavigationTree().navigateToHeavyCommercialVehicle( 3 );

    }
}
