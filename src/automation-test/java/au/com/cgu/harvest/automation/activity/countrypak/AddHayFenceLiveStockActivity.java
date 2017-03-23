package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;

@Activity( "Add Hay Fence and Live Stock Activity" )
public class AddHayFenceLiveStockActivity extends AbstractActivity< HayFencesLiveStockPage >
{
    private HarvestPage _page;

    public AddHayFenceLiveStockActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HayFencesLiveStockPage execute()
    {
        HayFencesLiveStockPage hayFencesLiveStockPage =
            _page.getNavigationTree().navigateToHayFenceLiveStock( 1 );

        hayFencesLiveStockPage.setBoundarySharedSumInsured( "1000" );
        hayFencesLiveStockPage.setBoundaryNotSharedSumInsured( "10000" );
        hayFencesLiveStockPage.setAllFencingSumInsured( "1000" );
        hayFencesLiveStockPage.setSheepSumInsured( "500" );
        hayFencesLiveStockPage.setHayGrainSectionTotalSumInsured( "1000" );
        hayFencesLiveStockPage.setFarmTreeSectionTotalSumInsured( "1000" );

        return hayFencesLiveStockPage;
    }
}
