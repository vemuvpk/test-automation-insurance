package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorMessageWhenlastSectionIsDeleted;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseErrorRulesForSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseMandatoryRulesForSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Error/Declined Rules for Countrypak Situations" )
public class ExerciseRulesForSituationScenario extends AbstractScenario
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

        SituationDetailPage situationLevelSectionPage =
            performActivity( new ExerciseMandatoryRulesForSectionsActivity( policyDetailPage ) );

        situationLevelSectionPage =
            performActivity( new ExerciseErrorRulesForSectionsActivity( situationLevelSectionPage ) );

        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 2 ) );
        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 1 ) );

        policyDetailPage =
            performActivity( new AssertErrorMessageWhenlastSectionIsDeleted( policyDetailPage ) );
    }
}
