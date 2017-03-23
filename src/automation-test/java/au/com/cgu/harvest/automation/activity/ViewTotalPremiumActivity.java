package au.com.cgu.harvest.automation.activity;

import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.TotalPremiumPage;

@Activity( "View the Total Premium page" )
public class ViewTotalPremiumActivity extends AbstractActivity< TotalPremiumPage >
{
    private HarvestPage _page;

    public ViewTotalPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected TotalPremiumPage execute()
    {
        TotalPremiumPage premiumPage = _page.getNavigationTree().navigateToTotalPremium();
        return premiumPage;
    }
}
