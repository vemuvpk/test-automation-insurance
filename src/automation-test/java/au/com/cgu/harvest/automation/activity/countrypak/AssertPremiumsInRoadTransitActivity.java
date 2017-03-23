package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Premiums in Road Transit Page Activity" )
public class AssertPremiumsInRoadTransitActivity extends
    AbstractActivity< RoadTransitPage >
{
    private HarvestPage _page;
    private String _sectionTotalPremium;

    public AssertPremiumsInRoadTransitActivity( HarvestPage page, String sectionTotalPremium )
    {
        _page = page;
        _sectionTotalPremium = sectionTotalPremium;
    }

    @Override
    protected RoadTransitPage execute()
    {
        RoadTransitPage roadTransitPage =
            _page.getNavigationTree().navigateToRoadTransit();

        Assert
            .assertEquals( _sectionTotalPremium,
                roadTransitPage.getBasePremium() );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.ROAD_TRANSIT );
        section.isTaken();

        return roadTransitPage;
    }
}
