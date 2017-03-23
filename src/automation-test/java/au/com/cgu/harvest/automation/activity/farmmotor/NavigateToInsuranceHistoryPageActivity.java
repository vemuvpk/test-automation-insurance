package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;

@Activity( "Navigate to Insurance History Page in Farm Motor" )
public class NavigateToInsuranceHistoryPageActivity extends AbstractActivity< InsuranceHistoryPage >

{
    private HarvestPage _page;

    public NavigateToInsuranceHistoryPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected InsuranceHistoryPage execute()
    {
        return _page.getNavigationTree().navigateToInsuranceHistory();

    }
}
