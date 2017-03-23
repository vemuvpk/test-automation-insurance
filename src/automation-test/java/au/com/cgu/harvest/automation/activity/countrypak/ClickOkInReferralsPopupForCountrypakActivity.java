package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish Policy as a Covernote for Countrypak after entering the Authorisation Code" )
public class ClickOkInReferralsPopupForCountrypakActivity extends
    AbstractActivity< NewBusinessPage >
{
    private OutstandingReferralsPopup _page;

    public ClickOkInReferralsPopupForCountrypakActivity( OutstandingReferralsPopup page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        OutstandingReferralsPopup referralPopup =
            _page.getNavigationTree().navigateToFinish().finishWithReferral();
        referralPopup.ok();
        // _page.clickOk();
        return new NewBusinessPage( getWebDriver() );
    }
}
