package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForRaedownActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Add a Countrypak With Manual Premium and then Issue and accept a policy scenario" )
public class ManualPermiumSection1Scenario extends AbstractScenario
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

        SituationDetailPage situationLevelSectionPage =
            performActivity( new CreateASituationForRaedownActivity( insuranceHistoryPage ) );

        // RoadTransitPage roadTransitPage =
        // performActivity( new AddRoadTransitForManualPremiumActivity( situationLevelSectionPage )
        // );
        //
        // DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
        // performActivity( new AddDomesticBuildingForManualPremiumActivity(
        // situationLevelSectionPage ) );
        //
        // domesticBuildingAndContentsPage =
        // performActivity( new AddDwellingTypeForManualPremium( domesticBuildingAndContentsPage )
        // );
        //
        // NewBusinessPage newBusinessPage =
        // performActivity( new FinishPolicyAsCoverNoteForCountrypakActivity(
        // insuranceHistoryPage ) );

        // newBusinessPage =
        // performActivity( new SaveAndAcceptActivity( newBusinessPage ) );

    }
}