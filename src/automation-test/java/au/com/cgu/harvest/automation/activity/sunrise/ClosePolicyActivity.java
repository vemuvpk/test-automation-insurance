package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Close Risk from sunrise executive" )
public class ClosePolicyActivity extends AbstractActivity< PolicyDetailPage >
{
    private NewBusinessPage _page;

    public ClosePolicyActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _page.close();
        return _page.closeRisk();
    }
}
