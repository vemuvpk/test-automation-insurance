package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.CancelDwellingTypePopupActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseMandatoryRulesForDwellingTypePopupActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseReferralRulesForDwellingTypePopupActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.DwellingPopUp;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Error/Declined Rules for Farm Property - Building Type Popup " )
public class ExerciseRulesForDomesticBuildingDwellingTypePopupScenario extends AbstractScenario
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
        DwellingPopUp dwellingPopUp =
            performActivity( new ExerciseMandatoryRulesForDwellingTypePopupActivity(
                policyDetailPage ) );

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new CancelDwellingTypePopupActivity(
                dwellingPopUp ) );

        // Referral Rules
        dwellingPopUp =
            performActivity( new ExerciseReferralRulesForDwellingTypePopupActivity(
                dwellingPopUp ) );

        domesticBuildingAndContentsPage =
            performActivity( new CancelDwellingTypePopupActivity(
                dwellingPopUp ) );

    }
}
