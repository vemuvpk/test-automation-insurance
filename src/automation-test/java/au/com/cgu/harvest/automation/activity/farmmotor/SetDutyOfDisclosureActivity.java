package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Set Duty of Disclosure to TRUE in the Policy Details Page" )
public class SetDutyOfDisclosureActivity extends AbstractActivity< PolicyDetailPage >
{
    private PolicyDetailPage _policyDetailPage;

    public SetDutyOfDisclosureActivity( PolicyDetailPage policyDetailPage )
    {
        _policyDetailPage = policyDetailPage;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _policyDetailPage.setDutyOfDisclosure( "true" );
        return _policyDetailPage;
    }
}
