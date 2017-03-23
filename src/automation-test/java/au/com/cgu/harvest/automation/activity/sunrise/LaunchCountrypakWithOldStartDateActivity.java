package au.com.cgu.harvest.automation.activity.sunrise;

import org.joda.time.LocalDate;

import au.com.cgu.harvest.automation.TestConfiguration;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Activity( "Launch Countrypak using default client and insured [SELENIUM] and an old Start date" )
public class LaunchCountrypakWithOldStartDateActivity extends AbstractActivity< PolicyDetailPage >
{
    private WelcomePage _welcomePage;

    public LaunchCountrypakWithOldStartDateActivity( WelcomePage welcomePage )
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
        newBusinessPage.setStartDate( new LocalDate().minusMonths( 13 )
            .toString( "dd MMM yyyy" ) );

        return newBusinessPage.addRiskDetails();
    }
}
