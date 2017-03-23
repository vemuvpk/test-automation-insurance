package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumRow;

@Activity( "Assert Premium Values In Read Only Premium Page Activity" )
public class AssertPremiumValuesInReadOnlyPageActivity extends AbstractActivity< PremiumPage >
{
    private HarvestPage _page;

    public AssertPremiumValuesInReadOnlyPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PremiumPage execute()
    {
        PremiumPage premiumPage = _page.getNavigationTree().navigateToTransactionPremium();

        PremiumRow row = premiumPage.getPremiumGrid().getPremiumAtRow( 1 );
        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );

        premiumPage.getPremiumGrid().collapseAll();
        premiumPage.getPremiumGrid().expandAll();

        return premiumPage;
    }
}
