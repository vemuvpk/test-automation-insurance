package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Delete hay Fences and Live Stock from Nav Tree Activity" )
public class DeleteHayFencesFromNavTreeActivity extends AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;

    public DeleteHayFencesFromNavTreeActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        PolicyDetailPage farmPropertyPage =
            _page.getNavigationTree().deleteHayFenceLiveStockAtPosition( 1 );

        return farmPropertyPage;
    }
}
