package au.com.cgu.harvest.automation.activity.sunrise;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Assert Policy Product, Stage and status from Harvest" )
public class AssertPolicyStageAndStatusActivity extends AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;
    private String _product;
    private String _stage;
    private String _status;

    public AssertPolicyStageAndStatusActivity( HarvestPage page, String product, String stage,
        String status )
    {
        _page = page;
        _product = product;
        _stage = stage;
        _status = status;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _page.getPolicyHeader().getPolicyProduct();
        _page.getPolicyHeader().getPolicyStage();
        _page.getPolicyHeader().getPolicyStatus();
        Assert.assertEquals( _product, _page.getPolicyHeader().getPolicyProduct() );
        Assert.assertEquals( _stage, _page.getPolicyHeader().getPolicyStage() );
        Assert.assertEquals( _status, _page.getPolicyHeader().getPolicyStatus() );
        return new PolicyDetailPage( getWebDriver() );
    }
}
