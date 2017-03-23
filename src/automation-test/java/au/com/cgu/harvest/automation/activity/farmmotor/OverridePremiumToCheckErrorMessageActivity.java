package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PremiumPage;

@Activity( "Override the system generated premium and check error messages" )
public class OverridePremiumToCheckErrorMessageActivity extends AbstractActivity< PremiumPage >
{
    private PremiumPage _page;
    private String _premiumOverrideAmount;

    public OverridePremiumToCheckErrorMessageActivity( PremiumPage page,
        String premiumOverrideAmount )
    {
        _page = page;
        _premiumOverrideAmount = premiumOverrideAmount;
    }

    @Override
    protected PremiumPage execute()
    {
        String originalTotalPremium = _page.getPremiumGrid().getFooterRow().getTotalPremium();
        _page.setTotalOverridePremium( _premiumOverrideAmount );
        String overriddenTotalPremium = _page.getPremiumGrid().getFooterRow().getTotalPremium();
        return _page;
    }
}
