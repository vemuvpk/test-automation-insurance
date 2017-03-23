package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Renew and Renew Risk Details for a policy from sunrise executive" )
public class RenewAndRenewRiskDetailsActivity extends AbstractActivity< PolicyDetailPage >
{
    private NewBusinessPage _page;

    public RenewAndRenewRiskDetailsActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _page.renew();
        PolicyDetailPage policyDetailPage = _page.renewRiskDetails();
        return policyDetailPage;

    }
}
