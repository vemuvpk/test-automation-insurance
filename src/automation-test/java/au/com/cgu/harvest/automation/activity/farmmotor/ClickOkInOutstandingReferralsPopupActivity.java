package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish Policy as a Covernote for Farm Motor after entering the Authorisation Code" )
public class ClickOkInOutstandingReferralsPopupActivity extends
    AbstractActivity< NewBusinessPage >
{
    private OutstandingReferralsPopup _popup;

    public ClickOkInOutstandingReferralsPopupActivity( OutstandingReferralsPopup popup )
    {
        _popup = popup;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _popup.ok();
        AbstractScenario.getScenarioLogger().trace( "Clicked OK in referral popup" );
        return new NewBusinessPage( getWebDriver() );
    }
}
