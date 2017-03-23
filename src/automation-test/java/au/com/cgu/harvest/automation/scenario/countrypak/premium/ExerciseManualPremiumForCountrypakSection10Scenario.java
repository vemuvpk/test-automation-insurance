package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ChangeRatingFactorInRoadTransitPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForRaedownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.EditManualPremiumValuesInRoadTransitPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToRoadTransitPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Manual Premium For Countrypak Section 10 - Road Transit Scenario" )
public class ExerciseManualPremiumForCountrypakSection10Scenario extends AbstractScenario
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
            performActivity( new CreateASituationForRaedownActivity(
                insuranceHistoryPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitWithManualPremiumActivity( situationLevelSectionPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( roadTransitPage ) );

        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        roadTransitPage =
            performActivity( new NavigateToRoadTransitPageActivity( policyDetailPage ) );

        ManuallyRatedRoadTransitPage.assertPremiumValues( roadTransitPage );
        roadTransitPage =
            performActivity( new EditManualPremiumValuesInRoadTransitPageActivity(
                roadTransitPage ) );

        newBusinessPage =
            performActivity( new SuspendActivity( roadTransitPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        roadTransitPage =
            performActivity( new NavigateToRoadTransitPageActivity( policyDetailPage ) );
        ManuallyRatedRoadTransitPage.assertNewPremiumValues( roadTransitPage );
        roadTransitPage =
            performActivity( new ChangeRatingFactorInRoadTransitPageActivity(
                roadTransitPage ) );
    }
}
