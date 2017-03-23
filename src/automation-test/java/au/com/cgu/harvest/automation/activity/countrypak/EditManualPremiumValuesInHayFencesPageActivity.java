package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;

@Activity( "Edit and Add new Premium Values to Hay Fence and Live Stock Activity" )
public class EditManualPremiumValuesInHayFencesPageActivity extends
    AbstractActivity< HayFencesLiveStockPage >
{
    private HarvestPage _page;

    public EditManualPremiumValuesInHayFencesPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HayFencesLiveStockPage execute()
    {
        HayFencesLiveStockPage hayFencesLiveStockPage =
            _page.getNavigationTree().navigateToHayFenceLiveStock( 1 );

        hayFencesLiveStockPage.setBoundaryNotSharedBasePremium( "66.66" );
        hayFencesLiveStockPage.setBoundarySharedBasePremium( "66.66" );
        hayFencesLiveStockPage.setAllOtherFencingBasePremium( "66.66" );
        hayFencesLiveStockPage.setSheepBasePremium( "66.66" );
        hayFencesLiveStockPage.setHayGrainBasePremium( "66.66" );
        hayFencesLiveStockPage.setFarmTreesBasePremium( "66.66" );

        return hayFencesLiveStockPage;
    }
}
