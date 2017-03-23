package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Renew Risk Details for a policy from sunrise executive" )
public class RenewRiskDetailsActivity extends AbstractActivity< PolicyDetailPage >
{
    private NewBusinessPage _page;

    public RenewRiskDetailsActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        PolicyDetailPage policyDetailPage = _page.renewRiskDetails();
        return policyDetailPage;
    }
}
