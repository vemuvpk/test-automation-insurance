package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelDomesticBuildingPage;

@Activity( "Add Domestic Building at Policy Level Activity" )
public class AddPolicyLevelDomesticBuildingActivity extends
    AbstractActivity< PolicyLevelDomesticBuildingPage >
{
    private HarvestPage _page;

    public AddPolicyLevelDomesticBuildingActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyLevelDomesticBuildingPage execute()
    {
        PolicyLevelDomesticBuildingPage policyLevelDomesticBuildingPage =
            _page.getNavigationTree().navigateToPolicyLevelDomesticBuilding();

        policyLevelDomesticBuildingPage.setCoverType( "Listed Events" );
        policyLevelDomesticBuildingPage.setExcess( "500.000" );
        policyLevelDomesticBuildingPage.setUnspecifiedValuableSumInsured( "100" );

        policyLevelDomesticBuildingPage.getSpecifiedValuablesGrid().addRow( "Description-1",
            "5000" );
        policyLevelDomesticBuildingPage.getSpecifiedValuablesGrid().addRow( "Description-2",
            "5000" );

        return policyLevelDomesticBuildingPage;
    }
}
