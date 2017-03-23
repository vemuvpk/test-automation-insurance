package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "View the policy details page" )
public class ViewPolicyDetailsActivity extends AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;

    public ViewPolicyDetailsActivity( HarvestPage harvestPage )
    {
        _page = harvestPage;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        return _page.getNavigationTree().navigateToPolicyDetails();
    }
}
