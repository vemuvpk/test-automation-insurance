package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;

@Activity( "Navigate to First Hay Fences and Live Stock Page in Countrypak" )
public class NavigateToHayFencesPageActivity extends
    AbstractActivity< HayFencesLiveStockPage >

{
    private HarvestPage _page;

    public NavigateToHayFencesPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HayFencesLiveStockPage execute()
    {
        return _page.getNavigationTree().navigateToHayFenceLiveStock( 1 );

    }
}
