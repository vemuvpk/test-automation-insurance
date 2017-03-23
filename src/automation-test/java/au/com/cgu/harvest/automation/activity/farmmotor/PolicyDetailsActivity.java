package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Complete all fields on the policy details page in a manner that allows a policy to be completed" )
public class PolicyDetailsActivity extends AbstractActivity< PolicyDetailPage >
{
    private static final String INSURANCE_SOLUTIONS_AGENT = "1264510";
    private PolicyDetailPage _policyDetailPage;

    public PolicyDetailsActivity( PolicyDetailPage policyDetailPage )
    {
        _policyDetailPage = policyDetailPage;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _policyDetailPage.setHeaderDetails( INSURANCE_SOLUTIONS_AGENT, false, true );
        return _policyDetailPage;
    }
}
