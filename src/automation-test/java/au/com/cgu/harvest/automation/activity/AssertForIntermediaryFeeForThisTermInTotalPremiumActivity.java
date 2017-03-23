package au.com.cgu.harvest.automation.activity;

import org.junit.Assert;

import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.TotalPremiumPage;

@Activity( "Assert Intermediary fees Details for this TERM in Total Premium page" )
public class AssertForIntermediaryFeeForThisTermInTotalPremiumActivity extends
    AbstractActivity< TotalPremiumPage >
{
    private HarvestPage _page;
    private String _intermediaryFees;
    private String _feesGST;
    private String _totalIntermediaryFees;

    public AssertForIntermediaryFeeForThisTermInTotalPremiumActivity( HarvestPage page,
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

        Assert.assertEquals( _intermediaryFees, premiumPage.getIntermediaryFeeForThisTerm() );
        Assert.assertEquals( _feesGST, premiumPage.getIntermediaryFeeGSTForThisTerm() );
        Assert.assertEquals( _totalIntermediaryFees,
            premiumPage.getTotalIntermediaryFeeForThisTerm() );
        return premiumPage;
    }
}
