package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Finish Policy as a New policy With Intermediary Statement and a referral present" )
public class FinishPolicyAsNewPolicyWithReferralActivity extends
    AbstractActivity< OutstandingReferralsPopup >
{
    private HarvestPage _page;

    public FinishPolicyAsNewPolicyWithReferralActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected OutstandingReferralsPopup execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Policy" );
        finishPage.setPolicyDeclaration( "Yes" );
        finishPage.setPremiumPaymentMethod( "Intermediary Statement" );
        return finishPage.finishWithReferral();
    }
}
