package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.CertificateOfCurrencyGridRow;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish Policy as a New policy" )
public class FinishPolicyAsNewPolicyWithTwoCertificateOfCurrencyActivity extends
    AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;
    private boolean _setPaymentMethodRequired;

    public FinishPolicyAsNewPolicyWithTwoCertificateOfCurrencyActivity( HarvestPage page,
        boolean setPaymentMethodRequired )
    {
        _page = page;
        _setPaymentMethodRequired = setPaymentMethodRequired;
    }

    public FinishPolicyAsNewPolicyWithTwoCertificateOfCurrencyActivity( HarvestPage page )
    {
        this( page, true );
    }

    @Override
    protected NewBusinessPage execute()
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

        CertificateOfCurrencyGridRow row2 =
            finishPage.getCertificateofCurrency().getCertificateAtRow( 2 );
        row2.setVehicleForCertificateOfCurrency();

        return finishPage.finish();
    }
}
