package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelFarmPropertyPage;

@Activity( "Navigate to Policy Level Farm Property Page in Countrypak" )
public class NavigateToPolicyLevelFarmPropertyPageActivity extends
    AbstractActivity< PolicyLevelFarmPropertyPage >

{
    private HarvestPage _page;

    public NavigateToPolicyLevelFarmPropertyPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyLevelFarmPropertyPage execute()
    {
        PolicyLevelFarmPropertyPage policyLevelFarmPropertyPage =
            _page.getNavigationTree().navigateToPolicyLevelFarmProperty();
        return policyLevelFarmPropertyPage;
    }
}
