package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.TestConfiguration;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.sunrise.LoginPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Activity( "Login to sunrise exchange using the default intermediary credentials [Broker, Broker, password]" )
public class LoginToExecutiveActivity extends AbstractActivity< WelcomePage >
{
    @Override
    protected WelcomePage execute()
    {
        getWebDriver().get(
            TestConfiguration.getCurrentTestConfiguration().getSunriseExecutiveUrl() );
        LoginPage loginPage = new LoginPage( getWebDriver() );

        loginPage.login( TestConfiguration.getCurrentTestConfiguration()
            .getIntermediaryDetails().getCompanyId(),
            TestConfiguration.getCurrentTestConfiguration()
                .getIntermediaryDetails().getUserId(),
            TestConfiguration.getCurrentTestConfiguration()
                .getIntermediaryDetails().getPassword() );
        return new WelcomePage( getWebDriver() );
    }
}
