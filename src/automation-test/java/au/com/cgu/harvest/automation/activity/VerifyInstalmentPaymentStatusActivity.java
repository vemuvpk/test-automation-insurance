package au.com.cgu.harvest.automation.activity;

import au.com.cgu.harvest.pages.InstalmentPaymentStatusPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Verify Instalment Payment Status Activity" )
public class VerifyInstalmentPaymentStatusActivity extends AbstractActivity< PolicyDetailPage >
{
    private PolicyDetailPage _page;

    public VerifyInstalmentPaymentStatusActivity( PolicyDetailPage page )
    {
        _page = page;

    }

    @Override
    protected PolicyDetailPage execute()
    {
        InstalmentPaymentStatusPopup popup = _page.getToolbar().getInstalmentPayment();
        popup.getTotalCollectedValue();
        popup.getTotalOutstandingValue();
        popup.getTotalPolicyValue();
        popup.closeInstalmentPlan();

        return _page;
    }
}
