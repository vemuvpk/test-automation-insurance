package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Delete Business Liability from the page and assert Total Loss Message Activity" )
public class DeleteBusinessLiabilityForTotalLossActivity extends
    AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;

    public DeleteBusinessLiabilityForTotalLossActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {

        _page.getNavigationTree().deleteBusinessLiabilitySectionForTotalLoss();

        return new PolicyDetailPage( getWebDriver() );
    }

}
