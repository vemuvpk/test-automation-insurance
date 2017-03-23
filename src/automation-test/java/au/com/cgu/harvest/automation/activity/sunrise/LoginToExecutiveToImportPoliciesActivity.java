package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.TestConfiguration;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.sunrise.IntermediaryDetails;
import au.com.cgu.harvest.pages.sunrise.LoginPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Activity( "Login to sunrise exchange to import policies" )
public class LoginToExecutiveToImportPoliciesActivity extends AbstractActivity< WelcomePage >
{
    private String _companyId;
    private String _userId;
    private String _password;
    private IntermediaryDetails _page;
    private LoginPage _loginPage;

    public LoginToExecutiveToImportPoliciesActivity( String companyId, String userId,
        String password )
    {
        _companyId = companyId;
        _userId = userId;
        _password = password;
    }

    @Override
    protected WelcomePage execute()
    {
        getWebDriver().get(
            TestConfiguration.getCurrentTestConfiguration().getSunriseExecutiveUrl() );
        LoginPage loginPage = new LoginPage( getWebDriver() );

        loginPage.login( _companyId, _userId, _password );
        return new WelcomePage( getWebDriver() );
    }
}
