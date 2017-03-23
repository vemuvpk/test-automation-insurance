package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseErrorRulesForBusinessLiabilityPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseMandatoryRulesForBusinessLiabilityActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseReferralRulesForBusinessLiabilityPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Error/Declined Rules for Business Liability Page" )
public class ExerciseRulesForBusinessLiabilityScenario extends AbstractScenario
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

        // Mandatory Rules
        BusinessLiabilityPage businessLiabilityPage =
            performActivity( new ExerciseMandatoryRulesForBusinessLiabilityActivity(
                policyDetailPage ) );
        // Error Rules
        businessLiabilityPage =
            performActivity( new ExerciseErrorRulesForBusinessLiabilityPageActivity(
                businessLiabilityPage ) );

        // Referral Rules
        businessLiabilityPage =
            performActivity( new
            ExerciseReferralRulesForBusinessLiabilityPageActivity(
                businessLiabilityPage ) );

    }
}
