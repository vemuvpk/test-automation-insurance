package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;

@Activity( "Add Hay Fence 2 Activity" )
public class AddHayFenceLiveStock2Activity extends AbstractActivity< HayFencesLiveStockPage >
{
    private HarvestPage _page;

    public AddHayFenceLiveStock2Activity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HayFencesLiveStockPage execute()
    {
        HayFencesLiveStockPage hayFencesLiveStockPage =
            _page.getNavigationTree().navigateToHayFenceLiveStock( 2 );

        hayFencesLiveStockPage.setBoundarySharedSumInsured( "5000" );
        hayFencesLiveStockPage.setBoundaryNotSharedSumInsured( "5000" );

        return hayFencesLiveStockPage;
    }
}
