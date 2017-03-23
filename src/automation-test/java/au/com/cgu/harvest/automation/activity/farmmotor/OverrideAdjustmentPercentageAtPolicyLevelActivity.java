package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PremiumPage;

@Activity( "Override the Adjustment percentage at Risk Level" )
public class OverrideAdjustmentPercentageAtPolicyLevelActivity extends
    AbstractActivity< PremiumPage >
{
    private PremiumPage _page;
    public String _riskLabel;

    public OverrideAdjustmentPercentageAtPolicyLevelActivity( PremiumPage page,
        String riskLabel )
    {
        _page = page;
        _riskLabel = riskLabel;
    }

    @Override
    protected PremiumPage execute()
    {
        _page.setPolicyAdjustmentPercentage( _riskLabel );

        return new PremiumPage( getWebDriver() );
    }
}
