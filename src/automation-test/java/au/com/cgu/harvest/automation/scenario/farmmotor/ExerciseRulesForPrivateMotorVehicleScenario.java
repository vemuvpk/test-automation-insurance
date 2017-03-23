package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AddADriverActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteDriverFromFirstVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseGlassesGuideAccessoriesAndModificationsGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseInterestedPartyGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseMandatoryRulesForPMVActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseReferralRulesForPMVActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Declined Rules for Private Motor Vehicle" )
public class ExerciseRulesForPrivateMotorVehicleScenario extends AbstractScenario
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
            performActivity( new ExerciseMandatoryRulesForPMVActivity( insuranceHistoryPage ) );

        VehicleSummaryPage vehicleSummaryPage =
            performActivity( new DeleteVehicleActivity< PrivateMotorVehiclePage >( vehiclePage )
            );

        vehiclePage =
            performActivity( new ExerciseReferralRulesForPMVActivity( insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new AddADriverActivity< PrivateMotorVehiclePage >( vehiclePage ) );

        vehiclePage =
            performActivity( new ExerciseInterestedPartyGridActivity< PrivateMotorVehiclePage >
            ( vehiclePage ) );
        vehiclePage =
            performActivity( new ExerciseGlassesGuideAccessoriesAndModificationsGridActivity< PrivateMotorVehiclePage >
            ( vehiclePage ) );
        vehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new AddADriverActivity< PrivateMotorVehiclePage >( vehiclePage ) );

        vehiclePage =
            performActivity( new DeleteDriverFromFirstVehicleActivity< PrivateMotorVehiclePage >(
                vehiclePage ) );
    }
}
