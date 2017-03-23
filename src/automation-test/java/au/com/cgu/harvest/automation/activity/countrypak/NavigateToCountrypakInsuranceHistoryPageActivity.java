package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;

@Activity( "Navigate to Business Interruption Page in Countrypak" )
public class NavigateToCountrypakInsuranceHistoryPageActivity extends
    AbstractActivity< CountrypakInsuranceHistoryPage >

{
    private HarvestPage _page;

    public NavigateToCountrypakInsuranceHistoryPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected CountrypakInsuranceHistoryPage execute()
    {
        return _page.getNavigationTree().navigateToCountrypakInsuranceHistory();

    }
}
