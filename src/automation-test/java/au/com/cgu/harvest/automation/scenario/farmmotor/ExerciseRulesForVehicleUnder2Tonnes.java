package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AddADriverActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddWellKnownFinanceTypeAndInterestedPartyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteDriverActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseGlassesGuideAccessoriesAndModificationsGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseInterestedPartyGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseMandatoryRulesForVehicleUnder2TonnesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseReferralRulesForVehicleUnder2TonnesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.VehicleUnder2TonnesAssertThatRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.IPageWithInterestedParties;
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Declined Rules for Vehicle Under 2 Tonnes" )
public class ExerciseRulesForVehicleUnder2Tonnes extends AbstractScenario
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

        VehicleUnder2TonnesPage vehiclePage =
            performActivity( new ExerciseMandatoryRulesForVehicleUnder2TonnesActivity(
                insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new ExerciseReferralRulesForVehicleUnder2TonnesActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new VehicleUnder2TonnesAssertThatRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new ExerciseInterestedPartyGridActivity< VehicleUnder2TonnesPage >
            ( vehiclePage ) );
        vehiclePage =
            performActivity( new ExerciseGlassesGuideAccessoriesAndModificationsGridActivity< VehicleUnder2TonnesPage >
            ( vehiclePage ) );
        vehiclePage =
            performActivity( new AddADriverActivity< VehicleUnder2TonnesPage >( vehiclePage ) );

        vehiclePage =
            performActivity( new DeleteDriverActivity< VehicleUnder2TonnesPage >( vehiclePage ) );

        IPageWithInterestedParties popup =
            performActivity( new AddWellKnownFinanceTypeAndInterestedPartyActivity( vehiclePage ) );

    }
}
