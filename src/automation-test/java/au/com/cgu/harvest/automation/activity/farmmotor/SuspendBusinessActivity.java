package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Activity to suspend any Vehicle" )
public class SuspendBusinessActivity< PAGE extends HarvestPage > extends
    AbstractActivity< NewBusinessPage >
{
    private PAGE _page;

    public SuspendBusinessActivity( PAGE page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        return _page.getToolbar().suspend();
    }
}
