package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithDraftActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToPolicyDetailPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStageAndStatusActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Add a Private Motor Vehicle with an Error Triggered and assert Finish Is Disabled and "
    + "Header says  - Draft" )
public class AddPrivateMotorVehicleWithDraftToCheckFinishDisabledScenario extends
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

        PrivateMotorVehiclePage vehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity(
                insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new CreatePrivateMotorVehicleWithDraftActivity( vehiclePage ) );

        policyDetailPage = performActivity( new NavigateToPolicyDetailPageActivity( vehiclePage ) );

        policyDetailPage =
            performActivity( new AssertPolicyStageAndStatusActivity( policyDetailPage,
                "Farm Motor",
                "New Business", "Draft" ) );

    }

}
