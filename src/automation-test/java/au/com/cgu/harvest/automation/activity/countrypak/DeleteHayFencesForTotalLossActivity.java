package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Delete Hay Fences for Total Loss for Situation 1 from the page and assert Total Loss Message Activity" )
public class DeleteHayFencesForTotalLossActivity extends
    AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;

    public DeleteHayFencesForTotalLossActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _page.getNavigationTree().deleteHayFencesSectionForTotalLoss( 1 );
        return new PolicyDetailPage( getWebDriver() );
    }
}
