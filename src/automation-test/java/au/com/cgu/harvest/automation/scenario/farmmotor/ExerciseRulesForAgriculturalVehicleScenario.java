package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AgriculturalVehicleAssertDeclinedRuleForVehicleValueActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AgriculturalVehicleAssertOtherTypeOfCropIsMandatoryWhenTypeOfCropIsSetToOtherActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AgriculturalVehicleAssertReferralForHowWillTheMachineBeUsedActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AgriculturalVehicleAssertReferralForTypeOfCoverActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AgriculturalVehicleAssertReferralForVehicleExistingDamageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AgriculturalVehicleAssertReferralRuleForLimitOfLiabilityActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AgriculturalVehicleAssertReferralRuleForTypeOfMachineActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AgriculturalVehicleAssertThatIsRegisteredCausesMandatoryErrorWhenChangedBackToDefaultActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AgriculturalVehicleAssertThatRegistrationNUmberIsMandatoryWhenVehicleIsSetToRegisteredActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AgriculturalVehicleAssertThatTypeOfCropIsMandatoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AgriculturalVehicleAssertThatVINIsMandatoryWhenVehicleIsSetToRegisteredActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseAccessoriesAndModificationsGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseInterestedPartyGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseMandatoryRulesForAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Declined Rules for Agricultural Motor Vehicle" )
public class ExerciseRulesForAgriculturalVehicleScenario extends AbstractScenario
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

        AgriculturalVehiclePage vehiclePage =
            performActivity( new ExerciseMandatoryRulesForAgriculturalVehicleActivity(
                insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new
            AgriculturalVehicleAssertOtherTypeOfCropIsMandatoryWhenTypeOfCropIsSetToOtherActivity(
                vehiclePage ) );
        vehiclePage =
            performActivity( new AgriculturalVehicleAssertThatTypeOfCropIsMandatoryActivity(
                vehiclePage ) );
        vehiclePage =
            performActivity( new
            AgriculturalVehicleAssertThatVINIsMandatoryWhenVehicleIsSetToRegisteredActivity(
                vehiclePage ) );
        vehiclePage =
            performActivity( new
            AgriculturalVehicleAssertThatRegistrationNUmberIsMandatoryWhenVehicleIsSetToRegisteredActivity(
                vehiclePage ) );
        vehiclePage =
            performActivity( new
            AgriculturalVehicleAssertThatIsRegisteredCausesMandatoryErrorWhenChangedBackToDefaultActivity(
                vehiclePage ) );

        // Referral Rules
        vehiclePage =
            performActivity( new AgriculturalVehicleAssertReferralForHowWillTheMachineBeUsedActivity(
                vehiclePage ) );
        vehiclePage =
            performActivity( new AgriculturalVehicleAssertReferralRuleForTypeOfMachineActivity(
                vehiclePage ) );
        vehiclePage =
            performActivity( new AgriculturalVehicleAssertReferralRuleForLimitOfLiabilityActivity(
                vehiclePage ) );
        vehiclePage =
            performActivity( new AgriculturalVehicleAssertDeclinedRuleForVehicleValueActivity(
                vehiclePage ) );
        vehiclePage =
            performActivity( new AgriculturalVehicleAssertReferralForVehicleExistingDamageActivity(
                vehiclePage ) );
        vehiclePage =
            performActivity( new AgriculturalVehicleAssertReferralForTypeOfCoverActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new ExerciseInterestedPartyGridActivity< AgriculturalVehiclePage >
            ( vehiclePage ) );
        vehiclePage =
            performActivity( new ExerciseAccessoriesAndModificationsGridActivity< AgriculturalVehiclePage >(
                vehiclePage ) );
    }
}
