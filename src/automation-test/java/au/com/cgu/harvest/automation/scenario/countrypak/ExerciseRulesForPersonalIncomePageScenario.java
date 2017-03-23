package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseCalculationsForPersonalIncomeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseMandatoryRulesForPersonalIncomeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseReferralRulesForPersonalIncomeActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Declined Rules for Personal Income page" )
public class ExerciseRulesForPersonalIncomePageScenario extends AbstractScenario
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

        PersonalIncomePage personalIncomePage =
            performActivity( new ExerciseMandatoryRulesForPersonalIncomeActivity( policyDetailPage
            ) );

        personalIncomePage =
            performActivity( new ExerciseReferralRulesForPersonalIncomeActivity( policyDetailPage
            ) );

        personalIncomePage =
            performActivity( new ExerciseCalculationsForPersonalIncomeActivity( policyDetailPage
            ) );
    }
}
