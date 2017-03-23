package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Terminate policy from sunrise executive" )
public class TerminatePolicyActivity extends AbstractActivity< PremiumPage >
{
    private NewBusinessPage _page;

    public TerminatePolicyActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected PremiumPage execute()
    {
        _page.terminate();
        _page.terminateRiskDetails();

        return new PremiumPage( getWebDriver() );
    }
}
