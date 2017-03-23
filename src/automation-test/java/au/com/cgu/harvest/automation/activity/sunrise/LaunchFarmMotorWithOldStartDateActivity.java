package au.com.cgu.harvest.automation.activity.sunrise;

import org.joda.time.LocalDate;

import au.com.cgu.harvest.automation.TestConfiguration;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Activity( "Launch FarmMotor using default client and insured [SELENIUM] and an old Start date" )
public class LaunchFarmMotorWithOldStartDateActivity extends AbstractActivity< PolicyDetailPage >
{
    private WelcomePage _welcomePage;

    public LaunchFarmMotorWithOldStartDateActivity( WelcomePage welcomePage )
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
            .getFarmMotorProduct() );
        newBusinessPage.setStartDate( new LocalDate().minusMonths( 9 )
            .toString( "dd MMM yyyy" ) );

        return newBusinessPage.addRiskDetails();
    }
}
