package au.com.cgu.harvest.automation.activity.farmmotor;

import org.junit.Assert;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Check for the policy dates in harvest Activity" )
public class AssertPolicyDateInHeaderActivity extends AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;
    private String _startDate;
    private String _endDate;
    private String _effectiveDate;

    public AssertPolicyDateInHeaderActivity( HarvestPage page, String startDate, String endDate,
        String effectiveDate )
    {
        _page = page;
        _startDate = startDate;
        _endDate = endDate;
        _effectiveDate = effectiveDate;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        PolicyDetailPage policyDetailPage = _page.getNavigationTree().navigateToPolicyDetails();
        Assert.assertEquals( _startDate, _page.getPolicyHeader().getPolicyStartDate() );
        Assert.assertEquals( _endDate, _page.getPolicyHeader().getPolicyEndDate() );
        Assert.assertEquals( _effectiveDate, _page.getPolicyHeader().getPolicyEffectiveDate() );
        return policyDetailPage;
    }
}
