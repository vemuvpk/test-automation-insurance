package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;

@Activity( "Navigate to Road Transit Page in Countrypak" )
public class NavigateToRoadTransitPageActivity extends
    AbstractActivity< RoadTransitPage >

{
    private HarvestPage _page;

    public NavigateToRoadTransitPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected RoadTransitPage execute()
    {
        return _page.getNavigationTree().navigateToRoadTransit();

    }
}
