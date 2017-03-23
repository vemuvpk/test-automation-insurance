package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPage;

@Activity( "View Interested parties Activity" )
public class ViewInterestedPartiesActivity extends AbstractActivity< InterestedPartiesPage >
{
    private HarvestPage _page;

    public ViewInterestedPartiesActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected InterestedPartiesPage execute()
    {
        InterestedPartiesPage interestedPartiesPage =
            _page.getNavigationTree().navigateToInterestedParties();

        return interestedPartiesPage;
    }
}
