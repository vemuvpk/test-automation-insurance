package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;

@Activity( "Navigate to Business Interruption Page in Countrypak" )
public class NavigateToBusinessInterruptionPageActivity extends
    AbstractActivity< BusinessInterruptionPage >

{
    private HarvestPage _page;

    public NavigateToBusinessInterruptionPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessInterruptionPage execute()
    {
        return _page.getNavigationTree().navigateToBusinessInterruption();

    }
}
