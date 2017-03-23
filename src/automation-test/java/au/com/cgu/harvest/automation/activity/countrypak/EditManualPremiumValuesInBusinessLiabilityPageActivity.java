package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;

@Activity( "Edit and Add new Premium Values to Business Interruption Activity" )
public class EditManualPremiumValuesInBusinessLiabilityPageActivity extends
    AbstractActivity< BusinessLiabilityPage >
{
    private HarvestPage _page;

    public EditManualPremiumValuesInBusinessLiabilityPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessLiabilityPage execute()
    {
        BusinessLiabilityPage businessLiabilityPage =
            _page.getNavigationTree().navigateToBusinessLiability();

        businessLiabilityPage.setBasePremiumValue( "66.66" );

        return businessLiabilityPage;
    }
}
