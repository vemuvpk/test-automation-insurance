package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add Road Transit - Sum Insured Activity" )
public class AddRoadTransitActivity extends AbstractActivity< RoadTransitPage >
{
    private HarvestPage _page;

    public AddRoadTransitActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected RoadTransitPage execute()
    {
        RoadTransitPage roadTransitPage =
            _page.getNavigationTree().navigateToRoadTransit();

        roadTransitPage.setRoadTransit();

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.ROAD_TRANSIT );
        section.isTaken();
        return roadTransitPage;
    }
}
