package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.InstalmentPlanPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "View Instalment plan Popup Activity" )
public class ViewInstalmentPlanInFinishPageActivity extends
    AbstractActivity< InstalmentPlanPopup >
{
    private FinishPage _page;

    public ViewInstalmentPlanInFinishPageActivity( FinishPage page )
    {
        _page = page;
    }

    @Override
    protected InstalmentPlanPopup execute()
    {
        _page.viewInstalmentPlan();

        return new InstalmentPlanPopup( getWebDriver() );
    }
}
