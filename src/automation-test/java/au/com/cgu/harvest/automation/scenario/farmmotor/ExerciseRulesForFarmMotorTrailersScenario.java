package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseAccessoriesAndModificationsGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseInterestedPartyGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseMandatoryRulesForFarmMotorTrailerActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseReferralRulesForFarmMotorTrailerActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FarmMotorTrailersAssertForIsRegisteredCausesMandatoryErrorWhenChangedBackToDefaultActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FarmMotorTrailersAssertForVehicleValidationActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FarmMotorTrailersAssertGaragedPostCodeCausesMandatoryErrorWhenDeletedActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FarmMotorTrailersAssertLimitOfLiabilityChangesForDifferentSituationsOfIsVehicleRegisteredActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FarmMotorTrailersAssertRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Declined Rules for Farm Motor Trailers" )
public class ExerciseRulesForFarmMotorTrailersScenario extends AbstractScenario
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

        FarmMotorTrailerPage vehiclePage =
            performActivity( new ExerciseMandatoryRulesForFarmMotorTrailerActivity(
                insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new ExerciseReferralRulesForFarmMotorTrailerActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new FarmMotorTrailersAssertForVehicleValidationActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new FarmMotorTrailersAssertLimitOfLiabilityChangesForDifferentSituationsOfIsVehicleRegisteredActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new FarmMotorTrailersAssertRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new FarmMotorTrailersAssertForIsRegisteredCausesMandatoryErrorWhenChangedBackToDefaultActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new FarmMotorTrailersAssertGaragedPostCodeCausesMandatoryErrorWhenDeletedActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new ExerciseInterestedPartyGridActivity< FarmMotorTrailerPage >
            ( vehiclePage ) );
        vehiclePage =
            performActivity( new ExerciseAccessoriesAndModificationsGridActivity< FarmMotorTrailerPage >(
                vehiclePage ) );
    }
}
