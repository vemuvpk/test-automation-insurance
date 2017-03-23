package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.TestConfiguration;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Activity( "Launch Countrypak using default client and insured [SELENIUM]" )
public class LaunchCountrypakActivity extends AbstractActivity< PolicyDetailPage >
{
    private WelcomePage _welcomePage;

    public LaunchCountrypakActivity( WelcomePage welcomePage )
    {
        _welcomePage = welcomePage;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        NewBusinessPage newBusinessPage = _welcomePage.addNewBusiness();
        newBusinessPage.setClient( TestConfiguration.getCurrentTestConfiguration()
            .getIntermediaryDetails().getClientId() );
        newBusinessPage.setInsured( TestConfiguration.getCurrentTestConfiguration()
            .getIntermediaryDetails().getInsuredId() );
        newBusinessPage.selectProduct( TestConfiguration.getCurrentTestConfiguration()
            .getCountrypakProduct() );

        return newBusinessPage.addRiskDetails();
    }
}
