package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;

@Activity( "Edit and Add new Premium Values to Business Interruption Activity" )
public class EditManualPremiumValuesInBusinessInterruptionPageActivity extends
    AbstractActivity< BusinessInterruptionPage >
{
    private HarvestPage _page;

    public EditManualPremiumValuesInBusinessInterruptionPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessInterruptionPage execute()
    {
        BusinessInterruptionPage businessInterruptionPage =
            _page.getNavigationTree().navigateToBusinessInterruption();

        businessInterruptionPage.setAgistmentBasePremium( "66.66" );
        businessInterruptionPage.setFarmContinuationBasePremium( "66.66" );

        return businessInterruptionPage;
    }
}
