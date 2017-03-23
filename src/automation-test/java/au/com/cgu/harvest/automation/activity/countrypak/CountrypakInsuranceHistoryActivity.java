package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;

@Activity( "Complete the Countrypak Insurance history page in a manner that allows a policy to be completed" )
public class CountrypakInsuranceHistoryActivity extends AbstractActivity< CountrypakInsuranceHistoryPage >
{
    private HarvestPage _page;

    public CountrypakInsuranceHistoryActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected CountrypakInsuranceHistoryPage execute()
    {
        CountrypakInsuranceHistoryPage insuranceHistoryPage =
            _page.getNavigationTree().navigateToCountrypakInsuranceHistory();

        insuranceHistoryPage.setHappyPathValues();
        return insuranceHistoryPage;
    }
}
