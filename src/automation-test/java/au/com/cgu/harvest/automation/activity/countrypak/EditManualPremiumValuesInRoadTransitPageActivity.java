package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;

@Activity( "Edit and Add new Premium Values to Road Transit Page Activity" )
public class EditManualPremiumValuesInRoadTransitPageActivity extends
    AbstractActivity< RoadTransitPage >
{
    private HarvestPage _page;

    public EditManualPremiumValuesInRoadTransitPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected RoadTransitPage execute()
    {
        RoadTransitPage roadTransitPage =
            _page.getNavigationTree().navigateToRoadTransit();

        roadTransitPage.setBasePremium( "66.66" );

        return roadTransitPage;
    }
}
