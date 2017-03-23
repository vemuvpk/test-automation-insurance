package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;

@Activity( "Add Hay Fence 2 Activity" )
public class AddHayFenceLiveStockToASituationActivity extends
    AbstractActivity< HayFencesLiveStockPage >
{
    private HarvestPage _page;
    private int _hayFences;

    public AddHayFenceLiveStockToASituationActivity( HarvestPage page, int hayFences )
    {
        _page = page;
        _hayFences = hayFences;
    }

    @Override
    protected HayFencesLiveStockPage execute()
    {
        HayFencesLiveStockPage hayFencesLiveStockPage =
            _page.getNavigationTree().navigateToHayFenceLiveStock( _hayFences );

        hayFencesLiveStockPage.setBoundarySharedSumInsured( "5000" );
        hayFencesLiveStockPage.setBoundaryNotSharedSumInsured( "5000" );

        return hayFencesLiveStockPage;
    }
}
