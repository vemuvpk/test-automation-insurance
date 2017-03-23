package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;

@Activity( "Complete the insurance history page in a manner that allows a policy to be completed" )
public class InsuranceHistoryActivity extends AbstractActivity< InsuranceHistoryPage >
{
    private HarvestPage _page;

    public InsuranceHistoryActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected InsuranceHistoryPage execute()
    {
        InsuranceHistoryPage insuranceHistoryPage =
            _page.getNavigationTree().navigateToInsuranceHistory();

        insuranceHistoryPage.setHappyPathValues();
        return insuranceHistoryPage;
    }
}
