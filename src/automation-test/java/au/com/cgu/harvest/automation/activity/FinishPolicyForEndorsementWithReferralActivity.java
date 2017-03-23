package au.com.cgu.harvest.automation.activity;

import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Finish the Endorsement with referral Activity" )
public class FinishPolicyForEndorsementWithReferralActivity extends
    AbstractActivity< OutstandingReferralsPopup >
{
    private FinishPage _page;

    public FinishPolicyForEndorsementWithReferralActivity( FinishPage page )
    {
        _page = page;
    }

    @Override
    protected OutstandingReferralsPopup execute()
    {

        _page.finishWithReferral();
        return new OutstandingReferralsPopup( getWebDriver() );

    }

}
