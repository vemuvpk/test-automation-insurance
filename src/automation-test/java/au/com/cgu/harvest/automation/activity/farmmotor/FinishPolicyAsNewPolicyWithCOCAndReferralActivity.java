package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.farmmotor.CertificateOfCurrencyGridRow;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Finish Policy as a New policy with CoC and referral" )
public class FinishPolicyAsNewPolicyWithCOCAndReferralActivity extends
    AbstractActivity< OutstandingReferralsPopup >
{
    private HarvestPage _page;
    private boolean _setPaymentMethodRequired;

    public FinishPolicyAsNewPolicyWithCOCAndReferralActivity( HarvestPage page,
        boolean setPaymentMethodRequired )
    {
        _page = page;
        _setPaymentMethodRequired = setPaymentMethodRequired;
    }

    public FinishPolicyAsNewPolicyWithCOCAndReferralActivity( HarvestPage page )
    {
        this( page, true );
    }

    @Override
    protected OutstandingReferralsPopup execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Policy" );
        finishPage.setPolicyDeclaration( "Yes" );
        finishPage.setCertificateOfCurrency( "true" );
        if ( _setPaymentMethodRequired )
        {
            finishPage.setPremiumPaymentMethod( "Intermediary Statement" );
        }

        CertificateOfCurrencyGridRow row1 =
            finishPage.getCertificateofCurrency().getCertificateAtRow( 1 );
        row1.setVehicleForCertificateOfCurrency();

        return finishPage.finishWithReferral();
    }
}
