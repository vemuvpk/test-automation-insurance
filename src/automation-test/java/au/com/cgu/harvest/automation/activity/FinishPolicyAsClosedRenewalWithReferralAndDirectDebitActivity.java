package au.com.cgu.harvest.automation.activity;

import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Finish Policy as a Closed Renewal And Direct Debit" )
public class FinishPolicyAsClosedRenewalWithReferralAndDirectDebitActivity extends
    AbstractActivity< FinishPage >
{
    private HarvestPage _page;

    public FinishPolicyAsClosedRenewalWithReferralAndDirectDebitActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FinishPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "In Force" );
        finishPage.setPremiumPaymentMethod( "Monthly Direct Debit" );
        finishPage.setIntermediaryFee( "150" );

        finishPage.setPremiumPaymentMethod( "Monthly Direct Debit" );
        finishPage.setIntermediaryFee( "150" );
        finishPage.setBankDetails( "012345", "123456", "Don Corleone" );
        finishPage.setDirectDebitAuthorisation( "Written/Electronic Request" );

        return finishPage;
    }
}
