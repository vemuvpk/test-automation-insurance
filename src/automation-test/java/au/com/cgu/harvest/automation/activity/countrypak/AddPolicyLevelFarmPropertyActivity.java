package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelFarmPropertyPage;

@Activity( "Add Policy Level Farm Property Activity" )
public class AddPolicyLevelFarmPropertyActivity extends
    AbstractActivity< PolicyLevelFarmPropertyPage >
{
    private HarvestPage _page;

    public AddPolicyLevelFarmPropertyActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyLevelFarmPropertyPage execute()
    {
        PolicyLevelFarmPropertyPage policyLevelFarmPropertyPage =
            _page.getNavigationTree().navigateToPolicyLevelFarmProperty();

        policyLevelFarmPropertyPage.setCoverType( "Listed Events" );
        policyLevelFarmPropertyPage.setExcess( "500.000" );

        policyLevelFarmPropertyPage.getSpecifiedItemsGrid().addRow( "Description-1",
            "5000" );
        policyLevelFarmPropertyPage.getSpecifiedItemsGrid().addRow( "Description-2",
            "5000" );
        return policyLevelFarmPropertyPage;
    }
}
