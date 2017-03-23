package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPageXpath;

@Activity( "Assert Referral For Manual Business Liability Page Activity" )
public class AssertReferralForManualBusinessLiabilityActivity extends
    AbstractActivity< BusinessLiabilityPage >
{
    private BusinessLiabilityPage _page;

    public AssertReferralForManualBusinessLiabilityActivity(
        BusinessLiabilityPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessLiabilityPage execute()
    {
        _page.setTransactionBasePremiumValue( "111.11" );

        _page.ruleTriggered(
            BusinessLiabilityPageXpath.TRANSACTION_BASE_PREMIUM, "R062" );

        return new BusinessLiabilityPage( getWebDriver() );

    }
}
