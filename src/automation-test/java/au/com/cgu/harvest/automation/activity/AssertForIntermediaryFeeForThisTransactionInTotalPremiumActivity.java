package au.com.cgu.harvest.automation.activity;

import org.junit.Assert;

import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.TotalPremiumPage;

@Activity( "Assert Intermediary fees Details for this TRANSACTION in Total Premium page" )
public class AssertForIntermediaryFeeForThisTransactionInTotalPremiumActivity extends
    AbstractActivity< TotalPremiumPage >
{
    private HarvestPage _page;
    private String _intermediaryFees;
    private String _feesGST;
    private String _totalIntermediaryFees;

    public AssertForIntermediaryFeeForThisTransactionInTotalPremiumActivity( HarvestPage page,
        String intermediaryFees, String feesGST, String totalIntermediaryFees )
    {
        _page = page;
        _intermediaryFees = intermediaryFees;
        _feesGST = feesGST;
        _totalIntermediaryFees = totalIntermediaryFees;
    }

    @Override
    protected TotalPremiumPage execute()
    {
        TotalPremiumPage premiumPage = _page.getNavigationTree().navigateToTotalPremium();

        Assert.assertEquals( _intermediaryFees, premiumPage.getIntermediaryFeeForThisTransaction() );
        Assert.assertEquals( _feesGST, premiumPage.getIntermediaryFeeGSTForThisTransaction() );
        Assert.assertEquals( _totalIntermediaryFees,
            premiumPage.getTotalIntermediaryFeeForThisTransaction() );
        return premiumPage;
    }
}
