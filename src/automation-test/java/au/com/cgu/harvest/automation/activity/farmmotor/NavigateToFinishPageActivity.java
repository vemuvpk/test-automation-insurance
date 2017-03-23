package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Navigate to Finish Page in Policy" )
public class NavigateToFinishPageActivity extends AbstractActivity< FinishPage >

{
    private HarvestPage _page;

    public NavigateToFinishPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FinishPage execute()
    {
        return _page.getNavigationTree().navigateToFinish();

    }
}
