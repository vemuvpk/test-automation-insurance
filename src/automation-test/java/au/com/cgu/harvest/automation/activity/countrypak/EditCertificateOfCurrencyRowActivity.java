package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CPCertificateOfCurrencyGridRow;
import au.com.cgu.harvest.pages.countrypak.CertificateOfCurrencyEditorPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Edit Dwelling Type inDomestic Buildings and cancel the popup Activity" )
public class EditCertificateOfCurrencyRowActivity extends
    AbstractActivity< CertificateOfCurrencyEditorPopup >
{
    private HarvestPage _page;
    private int _rowNumber;

    public EditCertificateOfCurrencyRowActivity( int rowNumber,
        HarvestPage page )
    {
        _page = page;
        _rowNumber = rowNumber;
    }

    @Override
    protected CertificateOfCurrencyEditorPopup execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "In Force" );
        finishPage.setPolicyDeclaration( "Yes" );
        finishPage.setCertificateOfCurrency( "true" );
        finishPage.setPremiumPaymentMethod( "Intermediary Statement" );

        CPCertificateOfCurrencyGridRow row =
            finishPage.getCountrypakCertificateofCurrency().getCertificateAtRow( _rowNumber );

        CertificateOfCurrencyEditorPopup editPopup = row.editRow();

        return new CertificateOfCurrencyEditorPopup( getWebDriver() );
    }
}
