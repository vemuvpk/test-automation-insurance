package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertAccessoriesAndModificationsPresentActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateTrailBikesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseAccessoriesAndModificationsGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToTrailBikesPageActivity;
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
@Scenario( "Assert Accessories And Modifications are present after suspend For Trail Bikes Scenario PH-944" )
public class AssertAccessoriesAndModificationsForTrailBikesScenario extends AbstractScenario
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

        FarmMotorTrailBikesPage vehiclePage =
            performActivity( new CreateTrailBikesActivity( insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new ExerciseAccessoriesAndModificationsGridActivity< FarmMotorTrailBikesPage >(
                vehiclePage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( vehiclePage ) );

        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        vehiclePage =
            performActivity( new NavigateToTrailBikesPageActivity( policyDetailPage ) );

        vehiclePage =
            performActivity( new AssertAccessoriesAndModificationsPresentActivity< FarmMotorTrailBikesPage >(
                vehiclePage ) );
    }
}
