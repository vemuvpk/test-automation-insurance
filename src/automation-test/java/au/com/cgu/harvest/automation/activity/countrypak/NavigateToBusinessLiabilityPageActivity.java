package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;

@Activity( "Navigate to Business Liability Page in Countrypak" )
public class NavigateToBusinessLiabilityPageActivity extends
    AbstractActivity< BusinessLiabilityPage >

{
    private HarvestPage _page;

    public NavigateToBusinessLiabilityPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessLiabilityPage execute()
    {
        return _page.getNavigationTree().navigateToBusinessLiability();

    }
}
