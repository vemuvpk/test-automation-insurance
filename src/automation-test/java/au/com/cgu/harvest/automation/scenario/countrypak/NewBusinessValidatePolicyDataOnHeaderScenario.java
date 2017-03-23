package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.joda.time.LocalDate;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ModifyStartDateToCheckReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyStartDateAndEndDateToCheckReferralActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "New Business - validate Policy data on Policy Header for Countrypak Scenario" )
public class NewBusinessValidatePolicyDataOnHeaderScenario extends
    AbstractScenario
{

    @Test
    public void execute()
    {
        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchCountrypakActivity( welcomePage ) );
        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        CountrypakInsuranceHistoryPage insuranceHistoryPage =
            performActivity( new CountrypakInsuranceHistoryActivity( policyDetailPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( policyDetailPage ) );
        policyDetailPage =
            performActivity( new ModifyStartDateToCheckReferralActivity( newBusinessPage,
                "18/05/2011", "R057" ) );
        newBusinessPage =
            performActivity( new SuspendActivity( policyDetailPage ) );
        policyDetailPage =
            performActivity( new ModifyStartDateToCheckReferralActivity( newBusinessPage,
                new LocalDate().plusMonths( 6 ).plusDays( 2 ).toString( "dd/MM/yyyy" ), "R058" ) );
        newBusinessPage =
            performActivity( new SuspendActivity( policyDetailPage ) );
        policyDetailPage =
            performActivity( new ModifyStartDateAndEndDateToCheckReferralActivity( newBusinessPage,
                "18/08/2011", "18/09/2011", "R059" ) );
        newBusinessPage =
            performActivity( new SuspendActivity( policyDetailPage ) );
        policyDetailPage =
            performActivity( new ModifyStartDateAndEndDateToCheckReferralActivity( newBusinessPage,
                "18/05/2020", "18/09/2021", "R060" ) );

    }
}
