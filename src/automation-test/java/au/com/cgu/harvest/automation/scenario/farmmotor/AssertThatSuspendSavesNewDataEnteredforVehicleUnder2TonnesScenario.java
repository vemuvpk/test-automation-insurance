package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertVehicleUnder2TonnesVehicleRatingDetails;
import au.com.cgu.harvest.automation.activity.farmmotor.ChangeVehicleUnder2TonnesVehicleRatingDetails;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateVehicleUnder2TonnesActivity;
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
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert that Suspend function saves the new data enetered for VehicleUnder2Tonnes" )
public class AssertThatSuspendSavesNewDataEnteredforVehicleUnder2TonnesScenario extends
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

        VehicleUnder2TonnesPage vehicleUnder2Tonnes =
            performActivity( new CreateVehicleUnder2TonnesActivity( insuranceHistoryPage ) );

        vehicleUnder2Tonnes =
            performActivity( new ChangeVehicleUnder2TonnesVehicleRatingDetails( vehicleUnder2Tonnes ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( vehicleUnder2Tonnes ) );

        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        vehicleUnder2Tonnes =
            performActivity( new AssertVehicleUnder2TonnesVehicleRatingDetails( vehicleUnder2Tonnes ) );

    }
}
