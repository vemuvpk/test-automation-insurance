package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.CertificateOfCurrencyGridRow;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish Policy as a New policy with CoC Activity" )
public class FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity extends
    AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;
    private int _timeout;
    private boolean _setPaymentMethodRequired;
    private static final int STANDARD_TIMEOUT = 5;

    public FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity( HarvestPage page,
        boolean setPaymentMethodRequired )
    {
        _page = page;
        _setPaymentMethodRequired = setPaymentMethodRequired;
    }

    public FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity( HarvestPage page )
    {
        this( page, true );
    }

    public FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity( HarvestPage page, int timeout )
    {
        this( page );
        _timeout = timeout;
    }

    @Override
    protected NewBusinessPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Policy" );
        finishPage.setPolicyDeclaration( "Yes" );
        finishPage.setCertificateOfCurrency( "Yes" );
        if ( _setPaymentMethodRequired )
        {
            finishPage.setPremiumPaymentMethod( "Intermediary Statement" );
        }

        CertificateOfCurrencyGridRow row1 =
            finishPage.getCertificateofCurrency().getCertificateAtRow( 1 );
        row1.setVehicleForCertificateOfCurrency();

        return finishPage.finish( _timeout == 0 ? STANDARD_TIMEOUT : _timeout );
    }
}
