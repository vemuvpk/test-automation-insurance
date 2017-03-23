package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertFalse;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PremiumPage;

@Activity( "Override the system generated premium" )
public class OverridePremiumActivity extends AbstractActivity< PremiumPage >
{
    private PremiumPage _page;
    private String _premiumOverrideAmount;

    public OverridePremiumActivity( PremiumPage page, String premiumOverrideAmount )
    {
        _page = page;
        _premiumOverrideAmount = premiumOverrideAmount;
    }

    @Override
    protected PremiumPage execute()
    {
        String originalTotalPremium = _page.getPremiumGrid().getFooterRow().getTotalPremium();
        System.out.println( "Original total premium: " + originalTotalPremium );
        _page.setTotalOverridePremium( _premiumOverrideAmount );

        String overriddenTotalPremium = _page.getPremiumGrid().getFooterRow().getTotalPremium();

        assertFalse( "Total premium should have been updated", originalTotalPremium.equals(
            overriddenTotalPremium ) );

        return _page;
    }
}
