package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseMandatoryRulesForFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.FarmPropertyAssertErrorRuleActivity;
import au.com.cgu.harvest.automation.activity.countrypak.FarmPropertyAssertReferralForInsuredSumExceedsTheLimitInNavTreeActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Error/Declined Rules for Farm Property Page" )
public class ExerciseRulesForFarmPropertyScenario extends AbstractScenario
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
        FarmPropertyPage farmPropertyPage =
            performActivity( new ExerciseMandatoryRulesForFarmPropertyActivity(
                policyDetailPage ) );

        // Referral Rules
        farmPropertyPage =
            performActivity( new
            FarmPropertyAssertReferralForInsuredSumExceedsTheLimitInNavTreeActivity(
                farmPropertyPage ) );

        // Error Rules
        farmPropertyPage =
            performActivity( new FarmPropertyAssertErrorRuleActivity(
                farmPropertyPage ) );

    }
}
