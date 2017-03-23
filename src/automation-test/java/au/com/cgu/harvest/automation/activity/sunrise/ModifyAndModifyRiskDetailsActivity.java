package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Modify and Modify Risk Details for a policy from sunrise executive" )
public class ModifyAndModifyRiskDetailsActivity extends AbstractActivity< PolicyDetailPage >
{
    private NewBusinessPage _page;

    public ModifyAndModifyRiskDetailsActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _page.modify();
        PolicyDetailPage policyDetailPage = _page.modifyRiskDetails();
        return policyDetailPage;

    }
}
