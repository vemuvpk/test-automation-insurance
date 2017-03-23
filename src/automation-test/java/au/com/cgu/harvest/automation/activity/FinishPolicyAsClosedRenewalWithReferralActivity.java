package au.com.cgu.harvest.automation.activity;

import au.com.cgu.harvest.pages.CreditCardDetailsPopup;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Finish Policy as a Closed Renewal And Credit Card" )
public class FinishPolicyAsClosedRenewalWithReferralActivity extends
    AbstractActivity< OutstandingReferralsPopup >
{
    private HarvestPage _page;

    public FinishPolicyAsClosedRenewalWithReferralActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected OutstandingReferralsPopup execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "In Force" );
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
