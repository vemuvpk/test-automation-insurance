package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPage;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesRow;

@Activity( "Assert Interested parties Activity" )
public class AssertInterestedPartiesActivity extends AbstractActivity< InterestedPartiesPage >
{
    private HarvestPage _page;

    private int _row;
    private String[] _attachTo;

    public AssertInterestedPartiesActivity( HarvestPage page, int row, String... attachTo )
    {
        _page = page;
        _attachTo = attachTo;
        _row = row;
    }

    @Override
    protected InterestedPartiesPage execute()
    {
        InterestedPartiesPage interestedPartiesPage =
            _page.getNavigationTree().navigateToInterestedParties();

        InterestedPartiesRow row =
            interestedPartiesPage.getInterestedPartiesGrid().getInterestedPartyAtRow( _row );

        row.hasAttachToLinks( _attachTo );

        return interestedPartiesPage;
    }
}
