package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;

@Activity( "Change Sum Insured in Road Transit Page Activity" )
public class ChangeDetailsInRoadTransitActivity extends
    AbstractActivity< RoadTransitPage >
{
    private RoadTransitPage _page;

    public ChangeDetailsInRoadTransitActivity( RoadTransitPage page )
    {
        _page = page;
    }

    @Override
    protected RoadTransitPage execute()
    {
        RoadTransitPage roadTransitPage =
            _page.getNavigationTree().navigateToRoadTransit();
        roadTransitPage.setSumInsured( "10,001" );

        return roadTransitPage;
    }
}
