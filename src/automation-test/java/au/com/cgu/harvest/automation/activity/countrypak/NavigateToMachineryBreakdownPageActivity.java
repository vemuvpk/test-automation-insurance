package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;

@Activity( "Navigate to Business Interruption Page in Countrypak" )
public class NavigateToMachineryBreakdownPageActivity extends
    AbstractActivity< MachineryBreakdownPage >

{
    private HarvestPage _page;

    public NavigateToMachineryBreakdownPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected MachineryBreakdownPage execute()
    {
        return _page.getNavigationTree().navigateToMachineryBreakdown();

    }
}
