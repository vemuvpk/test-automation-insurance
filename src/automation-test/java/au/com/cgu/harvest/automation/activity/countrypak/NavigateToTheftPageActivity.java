package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;

@Activity( "Navigate to Theft Page in Countrypak" )
public class NavigateToTheftPageActivity extends
    AbstractActivity< TheftPage >

{
    private HarvestPage _page;

    public NavigateToTheftPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected TheftPage execute()
    {
        return _page.getNavigationTree().navigateToTheft();

    }
}
