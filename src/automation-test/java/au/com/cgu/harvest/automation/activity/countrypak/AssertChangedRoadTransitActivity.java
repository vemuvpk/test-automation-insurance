package au.com.cgu.harvest.automation.activity.countrypak;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;

@Activity( "Asert that the changed details in Road Transit Page are saved after suspend activity" )
public class AssertChangedRoadTransitActivity extends AbstractActivity< RoadTransitPage >
{

    private RoadTransitPage _page;

    public AssertChangedRoadTransitActivity( RoadTransitPage page )
    {
        _page = page;
    }

    @Override
    protected RoadTransitPage execute()
    {
        RoadTransitPage roadTransitPage =
            _page.getNavigationTree().navigateToRoadTransit();
        assertEquals( "$10,001", _page.getSumInsured() );

        // _page.getNavigationTree().isRoadTransitTaken( 1 );

        return roadTransitPage;

    }

}
