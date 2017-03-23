package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CPCertificateOfCurrencyGridRow;
import au.com.cgu.harvest.pages.countrypak.CertificateOfCurrencyEditorPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Change Certificate of Currency from Policy level to Risk level in Finish Page, Assert for CoC Grid Values for Countrypak" )
public class ChangeCertificateOfCurrencyFromPolicyToRiskLevelActivity extends
    AbstractActivity< FinishPage >
{
    private HarvestPage _page;

    public ChangeCertificateOfCurrencyFromPolicyToRiskLevelActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FinishPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "In Force" );
        finishPage.setPolicyDeclaration( "Yes" );
        finishPage.setCertificateOfCurrency( "true" );
        finishPage.setPremiumPaymentMethod( "Intermediary Statement" );

        CPCertificateOfCurrencyGridRow row =
            finishPage.getCountrypakCertificateofCurrency().getCertificateAtRow( 1 );

        CertificateOfCurrencyEditorPopup editPopup = row.editRow();
        editPopup.setMarkedAttentionTo( "Mr. V V" );
        editPopup.setAdditionalInformation( "Additional information entered" );
        editPopup.attachToSection( "★Situation - 1 - Dwelling: Additional Details" );
        editPopup.ok();

        row.hasValues( "AGC Finance", "Hire Purchase", "PO Box 666 Coorparoo QLD 4151",
            "★Situation - 1 - Dwelling: Additional Details" );

        editPopup = row.editRow();
        editPopup.setOverrideAddress( "true" );
        editPopup.setAddressLine1( "New Address" );
        editPopup.setSuburbStatePostcode( "Sydney", "NSW", "2000" );
        editPopup.ok();

        row.hasValues( "AGC Finance", "Hire Purchase", "New Address SYDNEY NSW 2000",
            "★Situation - 1 - Dwelling: Additional Details" );
        return finishPage;
    }
}
