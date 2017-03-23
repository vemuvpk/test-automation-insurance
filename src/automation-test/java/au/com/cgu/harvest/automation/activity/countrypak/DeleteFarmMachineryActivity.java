package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;

@Activity( "Delete Farm Machinery and Working Dogs from the page and assert the Green Tick is gone Activity" )
public class DeleteFarmMachineryActivity extends AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;

    public DeleteFarmMachineryActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        FarmMachineryAndWorkingDogsPage farmMachineryPage =
            _page.getNavigationTree().navigateToFarmMachinery();

        farmMachineryPage.deleteFarmMachinery();

        return new PolicyDetailPage( getWebDriver() );

    }

}
