package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "View Risk Details from sunrise executive" )
public class ViewRiskDetailsActivity extends AbstractActivity< PolicyDetailPage >
{
    private NewBusinessPage _page;

    public ViewRiskDetailsActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        PolicyDetailPage policyDetailPage = _page.viewRiskDetails();
        return policyDetailPage;
    }
}
