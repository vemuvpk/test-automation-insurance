package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Finish Policy as a New Policy with Referral for Farm Motor" )
public class FinishPolicyAsNewPolicyWithReferralForFarmMotorActivity extends
    AbstractActivity< OutstandingReferralsPopup >
{
    private HarvestPage _page;

    public FinishPolicyAsNewPolicyWithReferralForFarmMotorActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected OutstandingReferralsPopup execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Policy" );
        finishPage.setPremiumPaymentMethod( "Intermediary Statement" );
        return finishPage.finishWithReferral();
    }
}
