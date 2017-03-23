package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseErrorRulesForMachineryBreakdownPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseMandatoryRulesForMachineryBreakdownPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Error/Declined Rules for Machinery Breakdown Page" )
public class ExerciseRulesForMachineryBreakdownScenario extends AbstractScenario
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
        MachineryBreakdownPage machineryBreakdownPage =
            performActivity( new ExerciseErrorRulesForMachineryBreakdownPageActivity(
                policyDetailPage ) );

        // Mandatory Rules
        machineryBreakdownPage =
            performActivity( new
            ExerciseMandatoryRulesForMachineryBreakdownPageActivity(
                machineryBreakdownPage ) );

    }
}
