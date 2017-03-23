package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CPCertificateOfCurrencyGridRow;
import au.com.cgu.harvest.pages.countrypak.CertificateOfCurrencyEditorPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish Policy as a New policy with CoC and assert for 2 IP's present" )
public class FinishPolicyAsNewPolicyWithCoCAndAssert2InterestedParties extends
    AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;

    public FinishPolicyAsNewPolicyWithCoCAndAssert2InterestedParties( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "In Force" );
        finishPage.setPolicyDeclaration( "Yes" );
        finishPage.setCertificateOfCurrency( "true" );
        finishPage.setPremiumPaymentMethod( "Intermediary Statement" );

        CPCertificateOfCurrencyGridRow row =
            finishPage.getCountrypakCertificateofCurrency().getCertificateAtRow( 1 );

        CertificateOfCurrencyEditorPopup editPopup = row.editRow();
        editPopup.ok();
        row.selectRow();

        CPCertificateOfCurrencyGridRow row2 =
            finishPage.getCountrypakCertificateofCurrency().getCertificateAtRow( 2 );
        row2.selectRow();

        return finishPage.finish();

    }
}
