package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.CancelBuildingTypePopupActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseMandatoryRulesForBuildingTypePopupActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseReferralRulesForBuildingTypePopupActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyBuildingTypePopUp;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Error/Declined Rules for Farm Property - Building Type Popup " )
public class ExerciseRulesForFarmPropertyBuildingTypePopupScenario extends AbstractScenario
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
        FarmPropertyBuildingTypePopUp farmPropertyBuildingTypePopUp =
            performActivity( new ExerciseMandatoryRulesForBuildingTypePopupActivity(
                policyDetailPage ) );

        // Referral Rules
        farmPropertyBuildingTypePopUp =
            performActivity( new ExerciseReferralRulesForBuildingTypePopupActivity(
                farmPropertyBuildingTypePopUp ) );

        FarmPropertyPage farmPropertyPage =
            performActivity( new CancelBuildingTypePopupActivity(
                farmPropertyBuildingTypePopUp ) );

    }
}
