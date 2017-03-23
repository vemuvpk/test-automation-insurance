package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AddWellKnownFinanceTypeAndInterestedPartyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseAccessoriesAndModificationsGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseInterestedPartyGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseMandatoryRulesForNonArticulatedTrailersActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseReferralRulesForNonArticulatedTrailersActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.IPageWithInterestedParties;
import au.com.cgu.harvest.pages.farmmotor.NonArticulatedTrailersPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Declined Rules for Non Articulated Trailers" )
public class ExerciseRulesForNonArticulatedTrailersScenario extends AbstractScenario
{
    @Test
    public void execute()
    {
        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchFarmMotorActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        InsuranceHistoryPage insuranceHistoryPage =
            performActivity( new InsuranceHistoryActivity( policyDetailPage ) );

        NonArticulatedTrailersPage vehiclePage =
            performActivity( new ExerciseMandatoryRulesForNonArticulatedTrailersActivity(
                insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new ExerciseReferralRulesForNonArticulatedTrailersActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new ExerciseInterestedPartyGridActivity< NonArticulatedTrailersPage >
            ( vehiclePage ) );
        vehiclePage =
            performActivity( new ExerciseAccessoriesAndModificationsGridActivity< NonArticulatedTrailersPage >(
                vehiclePage ) );

        IPageWithInterestedParties popup =
            performActivity( new AddWellKnownFinanceTypeAndInterestedPartyActivity( vehiclePage ) );

    }
}
