package au.com.cgu.harvest.automation.activity;

import org.junit.Assert;

import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Check for the Policy Stage and Policy Status in Header Activity" )
public class AssertPolicyStageAndStatusInHeaderActivity extends AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;
    private String _policyStage;
    private String _policyStatus;
    private String _paidBy;

    public AssertPolicyStageAndStatusInHeaderActivity( HarvestPage page, String policyStage,
        String policyStatus, String paidBy )
    {
        _page = page;
        _policyStage = policyStage;
        _policyStatus = policyStatus;
        _paidBy = paidBy;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        PolicyDetailPage policyDetailPage = _page.getNavigationTree().navigateToPolicyDetails();
        Assert.assertEquals( _policyStage, _page.getPolicyHeader().getPolicyStage() );
        Assert.assertEquals( _policyStatus, _page.getPolicyHeader().getPolicyStatus() );
        Assert.assertEquals( _paidBy, _page.getPolicyHeader().getPolicyPaidBy() );
        return policyDetailPage;
    }
}
