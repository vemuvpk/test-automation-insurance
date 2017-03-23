package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.sunrise.ImportPolicyPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Activity( "Launch Import Policy using default client and insured [1,1]" )
public class LaunchImportPolicyActivity extends AbstractActivity< ImportPolicyPage >
{
    private WelcomePage _welcomePage;

    public LaunchImportPolicyActivity( WelcomePage welcomePage )
    {
        _welcomePage = welcomePage;
    }

    @Override
    protected ImportPolicyPage execute()
    {
        _welcomePage.importPolicies();
        return new ImportPolicyPage( getWebDriver() );
    }
}
