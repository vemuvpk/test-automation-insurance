package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelDomesticBuildingPage;

@Activity( "Navigate to Policy Level Domestic Building Page in Countrypak" )
public class NavigateToPolicyLevelDomesticBuildingActivity extends
    AbstractActivity< PolicyLevelDomesticBuildingPage >

{
    private HarvestPage _page;

    public NavigateToPolicyLevelDomesticBuildingActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyLevelDomesticBuildingPage execute()
    {
        PolicyLevelDomesticBuildingPage policyLevelDomesticBuildingPage =
            _page.getNavigationTree().navigateToPolicyLevelDomesticBuilding();
        return policyLevelDomesticBuildingPage;
    }
}
