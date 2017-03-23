package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertTrailBikesVehicleRatingDetails;
import au.com.cgu.harvest.automation.activity.farmmotor.ChangeTrailBikesVehicleRatingDetails;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateTrailBikesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert that Suspend function saves the new data enetered for farm Motor Trail Bike" )
public class AssertThatSuspendSavesNewDataEnteredforFarmMotorTrailBikesScenario extends
    AbstractScenario
{
    @Test
    public void execute()
    {
        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchFarmMotorActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        InsuranceHistoryPage insuranceHistoryPage =
            performActivity( new InsuranceHistoryActivity( policyDetailPage ) );

        FarmMotorTrailBikesPage farmMotorTrailBikesPage =
            performActivity( new CreateTrailBikesActivity( insuranceHistoryPage ) );

        farmMotorTrailBikesPage =
            performActivity( new ChangeTrailBikesVehicleRatingDetails( farmMotorTrailBikesPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( farmMotorTrailBikesPage ) );

        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        farmMotorTrailBikesPage =
            performActivity( new AssertTrailBikesVehicleRatingDetails( farmMotorTrailBikesPage ) );

    }
}
