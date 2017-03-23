package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;

@Activity( "Set TRUE to New Question-Answered in respect to all drivers in Insurance History Page" )
public class SetAnsweredForAllDriversInInsuranceHistoryPageActivity extends
    AbstractActivity< InsuranceHistoryPage >
{
    private HarvestPage _page;

    public SetAnsweredForAllDriversInInsuranceHistoryPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected InsuranceHistoryPage execute()
    {
        InsuranceHistoryPage insuranceHistoryPage =
            _page.getNavigationTree().navigateToInsuranceHistory();

        insuranceHistoryPage.setAnsweredForAllDrivers( "true" );

        return insuranceHistoryPage;

    }
}
