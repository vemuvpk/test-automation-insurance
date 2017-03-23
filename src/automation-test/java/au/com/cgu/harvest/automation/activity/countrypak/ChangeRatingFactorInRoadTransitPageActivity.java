package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;

@Activity( "Change Rating Factor and Assert that Premium value is Blanked out Activity" )
public class ChangeRatingFactorInRoadTransitPageActivity extends
    AbstractActivity< RoadTransitPage >
{
    private HarvestPage _page;

    public ChangeRatingFactorInRoadTransitPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected RoadTransitPage execute()
    {
        RoadTransitPage roadTransitPage =
            _page.getNavigationTree().navigateToRoadTransit();

        roadTransitPage.setSumInsured( "666" );
        Assert.assertEquals( "", roadTransitPage.getBasePremium() );

        return roadTransitPage;
    }
}
