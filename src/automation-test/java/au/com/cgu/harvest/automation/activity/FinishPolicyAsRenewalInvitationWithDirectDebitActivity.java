package au.com.cgu.harvest.automation.activity;

import org.junit.Assert;

import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InstalmentPlanPopup;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Finish Policy as a Renewal Invitation And Direct Debit" )
public class FinishPolicyAsRenewalInvitationWithDirectDebitActivity extends
    AbstractActivity< OutstandingReferralsPopup >
{
    private HarvestPage _page;

    public FinishPolicyAsRenewalInvitationWithDirectDebitActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected OutstandingReferralsPopup execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Invitation" );
        finishPage.setPremiumPaymentMethod( "Monthly Direct Debit" );
        Assert
            .assertEquals(
                "Instalment deduction will not commence until this transaction is closed. Late closing may result in a change to the Instalment plan.",
                finishPage.getMessageForCreditCard() );

        finishPage.setIntermediaryFee( "150" );

        finishPage.setBankDetails( "012345", "123456", "Don Corleone" );
        InstalmentPlanPopup instalmentPlan = finishPage.viewInstalmentPlan();
        instalmentPlan.getIntermediaryFees();
        instalmentPlan.closeInstalmentPlan();
        finishPage.setDirectDebitAuthorisation( "Verbal Authority" );

        return finishPage.finishWithReferral();
    }
}
