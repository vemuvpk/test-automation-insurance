package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;

@Activity( "Enter the Authorisation Code in the Outstanding Referral Popup" )
public class AddAuthorisationCodeActivity extends
    AbstractActivity< OutstandingReferralsPopup >
{
    private OutstandingReferralsPopup _page;
    private String _arn;

    public AddAuthorisationCodeActivity( OutstandingReferralsPopup page, String arn )
    {
        _page = page;
        _arn = arn;
    }

    @Override
    protected OutstandingReferralsPopup execute()
    {
        _page.setAuthorisationCode( _arn );
        return _page;
    }
}
