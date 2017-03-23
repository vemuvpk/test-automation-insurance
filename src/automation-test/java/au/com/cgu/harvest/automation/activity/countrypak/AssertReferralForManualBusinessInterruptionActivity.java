package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPageXpath;

@Activity( "Assert Referral For Manual Business Interruption Page Activity" )
public class AssertReferralForManualBusinessInterruptionActivity extends
    AbstractActivity< BusinessInterruptionPage >
{
    private BusinessInterruptionPage _page;

    public AssertReferralForManualBusinessInterruptionActivity(
        BusinessInterruptionPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessInterruptionPage execute()
    {
        _page.setAgistmentBasePremium( "111.11" );
        _page.setFarmContinuationBasePremium( "111.11" );

        _page.ruleTriggered( BusinessInterruptionPageXpath.AGISTMENT_INCOME_BASE_PREMIUM,
            "R062" );
        _page.ruleTriggered( BusinessInterruptionPageXpath.FARM_CONTINUATION_EXPENSES_BASE_PREMIUM,
            "R062" );

        return new BusinessInterruptionPage( getWebDriver() );

    }
}
