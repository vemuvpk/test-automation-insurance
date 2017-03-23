package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseErrorRulesForBusinessInterruptionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseReferralRulesForBusinessInterruptionPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "CFMC-74" )
@Scenario( "Exercise Mandatory, Referral and Error/Declined Rules for Business Interruption Page" )
public class ExerciseRulesForBusinessInterruptionScenario extends AbstractScenario
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

        // Error Rules
        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new ExerciseErrorRulesForBusinessInterruptionPageActivity(
                policyDetailPage ) );

        // Referral Rules
        businessInterruptionPage =
            performActivity( new
            ExerciseReferralRulesForBusinessInterruptionPageActivity(
                businessInterruptionPage ) );

    }
}
