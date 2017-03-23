package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Enter the Authorisation Code in the Outstanding Referral Popup and Finish Activity" )
public class AddAuthorisationCodeAndFinishActivity extends
    AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;

    public AddAuthorisationCodeAndFinishActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        OutstandingReferralsPopup referralPopup =
            _page.getNavigationTree().navigateToFinish().finishWithReferral();
        referralPopup.setAuthorisationCode( "999999" );
        referralPopup.ok();
        return new NewBusinessPage( getWebDriver() );

    }
}
