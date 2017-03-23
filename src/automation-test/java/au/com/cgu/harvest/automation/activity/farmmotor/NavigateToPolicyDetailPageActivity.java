package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Navigate to Policy Details Page in Farm Motor" )
public class NavigateToPolicyDetailPageActivity extends AbstractActivity< PolicyDetailPage >

{
    private HarvestPage _page;

    public NavigateToPolicyDetailPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        return _page.getNavigationTree().navigateToPolicyDetails();

    }
}
