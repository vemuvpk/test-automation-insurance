package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;

@Activity( "Change Rating Factor and Assert that Premium value is Blanked out Activity" )
public class ChangeRatingFactorInHayFencesPageActivity extends
    AbstractActivity< HayFencesLiveStockPage >
{
    private HarvestPage _page;

    public ChangeRatingFactorInHayFencesPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HayFencesLiveStockPage execute()
    {
        HayFencesLiveStockPage hayFencesLiveStockPage =
            _page.getNavigationTree().navigateToHayFenceLiveStock( 1 );

        hayFencesLiveStockPage.setBoundarySharedSumInsured( "2000" );
        hayFencesLiveStockPage.setBoundaryNotSharedSumInsured( "20000" );

        Assert.assertEquals( "", hayFencesLiveStockPage.getBoundaryNotSharedPremiumValue() );
        Assert.assertEquals( "", hayFencesLiveStockPage.getBoundarySharedPremiumValue() );

        return hayFencesLiveStockPage;
    }
}
