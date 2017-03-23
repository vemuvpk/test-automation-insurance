package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertChangedRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ChangeDetailsInRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendBusinessActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert That Suspend saves New data enetered for a Road Transit Scenario" )
public class AssertThatSuspendSavesNewDataEneteredForRoadTransitScenario extends AbstractScenario
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

        SituationDetailPage situationDetailpage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                policyDetailPage ) );
        RoadTransitPage roadTransitpage =
            performActivity( new AddRoadTransitActivity( policyDetailPage ) );

        roadTransitpage =
            performActivity( new ChangeDetailsInRoadTransitActivity( roadTransitpage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendBusinessActivity< SituationDetailPage >(
                situationDetailpage ) );

        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        roadTransitpage =
            performActivity( new AssertChangedRoadTransitActivity( roadTransitpage ) );

    }
}
