package au.com.cgu.harvest.automation.activity;

import org.junit.Assert;

import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PremiumPage;

@Activity( "Get Count of Risks including deleted risks in Transaction Premium Page" )
public class GetCountOfRisksInTransactionPremiumActivity extends AbstractActivity< PremiumPage >
{
    private HarvestPage _page;
    private int _rows;

    public GetCountOfRisksInTransactionPremiumActivity( HarvestPage page, int rows )
    {
        _page = page;
        _rows = rows;
    }

    @Override
    protected PremiumPage execute()
    {
        PremiumPage premiumPage = _page.getNavigationTree().navigateToTransactionPremium();
        premiumPage.getPremiumGrid().getNumberOfRisks();
        Assert.assertEquals( _rows, premiumPage.getPremiumGrid().getNumberOfRisks() );
        return premiumPage;
    }
}
