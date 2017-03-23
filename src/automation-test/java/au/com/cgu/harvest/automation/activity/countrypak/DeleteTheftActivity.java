package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;

@Activity( "Delete Theft from the page and assert the Green Tick is gone Activity" )
public class DeleteTheftActivity extends AbstractActivity< TheftPage >
{
    private HarvestPage _page;

    public DeleteTheftActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected TheftPage execute()
    {
        TheftPage theftPage =
            _page.getNavigationTree().navigateToTheft();

        theftPage.deleteTheft();

        return theftPage;
    }

}
