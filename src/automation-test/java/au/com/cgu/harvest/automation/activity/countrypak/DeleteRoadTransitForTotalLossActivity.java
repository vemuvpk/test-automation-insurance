package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Delete Road Transit from the page and assert Total Loss Message Activity" )
public class DeleteRoadTransitForTotalLossActivity extends AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;

    public DeleteRoadTransitForTotalLossActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {

        _page.getNavigationTree().deleteRoadTransitSectionForTotalLoss();

        return new PolicyDetailPage( getWebDriver() );
    }

}
