package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Suspend and return to the broker management system" )
public class SuspendActivity extends AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;

    public SuspendActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        return _page.getToolbar().suspend();
    }
}
