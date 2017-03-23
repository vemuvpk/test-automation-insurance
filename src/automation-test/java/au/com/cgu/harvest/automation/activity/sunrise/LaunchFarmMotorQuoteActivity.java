package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.TestConfiguration;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Activity( "Launch Farm Motor Quote using default client and insured [SELENIUM]" )
public class LaunchFarmMotorQuoteActivity extends AbstractActivity< PolicyDetailPage >
{
    private WelcomePage _welcomePage;

    public LaunchFarmMotorQuoteActivity( WelcomePage welcomePage )
    {
        _welcomePage = welcomePage;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        NewBusinessPage newQuote = _welcomePage.addNewQuote();
        newQuote.setClient( TestConfiguration.getCurrentTestConfiguration()
            .getIntermediaryDetails().getClientId() );
        newQuote.setInsured( TestConfiguration.getCurrentTestConfiguration()
            .getIntermediaryDetails().getInsuredId() );
        newQuote.selectProduct( TestConfiguration.getCurrentTestConfiguration()
            .getFarmMotorProduct() );

        return newQuote.addRiskDetails();
    }
}
