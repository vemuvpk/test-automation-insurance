package au.com.cgu.harvest.automation.activity;

import org.junit.Assert;

import au.com.cgu.harvest.pages.CreditCardDetailsPopup;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Finish Policy as a Renewal Invitation And Credit Card with a referral" )
public class FinishPolicyAsRenewalInvitationWithReferralActivity extends
    AbstractActivity< OutstandingReferralsPopup >
{
    private HarvestPage _page;

    public FinishPolicyAsRenewalInvitationWithReferralActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected OutstandingReferralsPopup execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Invitation" );
        finishPage.setPremiumPaymentMethod( "Monthly Credit Card" );
        Assert
            .assertEquals(
                "Instalment deduction will not commence until this transaction is closed. Late closing may result in a change to the Instalment plan.",
                finishPage.getMessageForCreditCard() );
        finishPage.setPremiumPaymentMethod( "Monthly Direct Debit" );
        Assert
            .assertEquals(
                "Instalment deduction will not commence until this transaction is closed. Late closing may result in a change to the Instalment plan.",
                finishPage.getMessageForCreditCard() );

        finishPage.setPremiumPaymentMethod( "Monthly Credit Card" );
        finishPage.setIntermediaryFee( "150" );

        CreditCardDetailsPopup creditCardPopup = finishPage.addCreditCardDetails();
        creditCardPopup.setCreditCardNumber( "4242424242424242" );
        creditCardPopup.setCreditCardName( "Credit Card Name" );
        creditCardPopup.setExpiryDate( "6", "2015" );
        creditCardPopup.clickCaptureCreditCardDetails();
        finishPage.setDirectDebitAuthorisation( "Written/Electronic Request" );
        return finishPage.finishWithReferral();
    }
}
