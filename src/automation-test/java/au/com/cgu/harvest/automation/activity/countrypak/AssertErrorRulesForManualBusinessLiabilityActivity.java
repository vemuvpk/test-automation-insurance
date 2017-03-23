package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPageXpath;

@Activity( "Assert Error Rules For Manual Business Liability Page Activity" )
public class AssertErrorRulesForManualBusinessLiabilityActivity extends
    AbstractActivity< BusinessLiabilityPage >
{
    private BusinessLiabilityPage _page;

    public AssertErrorRulesForManualBusinessLiabilityActivity(
        BusinessLiabilityPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessLiabilityPage execute()
    {
        _page.ruleTriggered(
            BusinessLiabilityPageXpath.TRANSACTION_BASE_PREMIUM, "E020" );

        return new BusinessLiabilityPage( getWebDriver() );

    }
}
