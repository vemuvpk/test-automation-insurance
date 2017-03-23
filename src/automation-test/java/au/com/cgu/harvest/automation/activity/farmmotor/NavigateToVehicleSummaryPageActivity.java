package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;

@Activity( "Navigate to Vehicle Summary Page in Farm Motor" )
public class NavigateToVehicleSummaryPageActivity extends AbstractActivity< VehicleSummaryPage >

{
    private HarvestPage _page;

    public NavigateToVehicleSummaryPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected VehicleSummaryPage execute()
    {
        return _page.getNavigationTree().navigateToVehicleSummaryPage();

    }
}
