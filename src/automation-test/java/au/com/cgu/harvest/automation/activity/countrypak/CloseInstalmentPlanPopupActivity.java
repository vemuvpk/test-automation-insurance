package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.InstalmentPlanPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Close Instalment Plan Popup Activity" )
public class CloseInstalmentPlanPopupActivity extends
    AbstractActivity< FinishPage >
{
    private InstalmentPlanPopup _page;

    public CloseInstalmentPlanPopupActivity( InstalmentPlanPopup page )
    {
        _page = page;
    }

    @Override
    protected FinishPage execute()
    {

        _page.closeInstalmentPlan();

        return new FinishPage( getWebDriver() );
    }
}
