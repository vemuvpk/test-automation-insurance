package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PremiumPage;

@Activity( "Reset the premium to whatever was generated by harvest - discard adjustments and overrides" )
public class ResetPremiumActivity extends AbstractActivity< PremiumPage >
{
    private PremiumPage _page;

    public ResetPremiumActivity( PremiumPage page )
    {
        _page = page;
    }

    @Override
    protected PremiumPage execute()
    {
        _page.reset();
        return _page;
    }
}