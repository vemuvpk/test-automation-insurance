package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PremiumPage;

@Activity( "View the Transaction Premium Page" )
public class ViewPremiumActivity extends AbstractActivity< PremiumPage >
{
    private HarvestPage _page;

    public ViewPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PremiumPage execute()
    {
        PremiumPage premiumPage = _page.getNavigationTree().navigateToTransactionPremium();
        return premiumPage;

    }
}
