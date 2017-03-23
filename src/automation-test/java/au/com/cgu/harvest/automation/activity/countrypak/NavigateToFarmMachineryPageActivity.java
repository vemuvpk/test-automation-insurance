package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;

@Activity( "Navigate to Farm Machinery and Working Dogs Page in Countrypak" )
public class NavigateToFarmMachineryPageActivity extends
    AbstractActivity< FarmMachineryAndWorkingDogsPage >

{
    private HarvestPage _page;

    public NavigateToFarmMachineryPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMachineryAndWorkingDogsPage execute()
    {
        return _page.getNavigationTree().navigateToFarmMachinery();

    }
}
