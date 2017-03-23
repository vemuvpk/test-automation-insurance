package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;

@Activity( "Navigate to Heavy Commercial Vehicle page in Farm Motor" )
public class NavigateToTrailBikesPageActivity extends
    AbstractActivity< FarmMotorTrailBikesPage >

{
    private HarvestPage _page;

    public NavigateToTrailBikesPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        return _page.getNavigationTree().navigateToFarmMotorTrailBikes( 1 );

    }
}
