package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseAccessoriesAndModificationsGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseInterestedPartyGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseMandatoryRulesForFarmMotorTrailBikesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseReferralRulesForFarmMotorTrailBikesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.TrailBikesAssertForGaragePostCodeCausesMandatoryErrorWhenDeletedActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.TrailBikesAssertForIsRegisteredCausesErrorWhenChangedBackToDefaultActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.TrailBikesAssertForRegistrationNumberIsMandatoryWhenIsRegisteredSetToYesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.TrailBikesAssertForVINIsMandatoryWhenVehicleIsRegisteredActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.TrailBikesAssertForVehicleValueWhenDeletedCausesErrorActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.TrailBikesAssertLimitOfLiabilityChangesForDifferentSituationsofIsVehicleRegisteredActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Decline Rules for farm Motor Trail Bikes Scenario" )
public class ExerciseRulesForTrailBikesScenario extends AbstractScenario
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
            performActivity( new ExerciseMandatoryRulesForFarmMotorTrailBikesActivity(
                insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new ExerciseReferralRulesForFarmMotorTrailBikesActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new TrailBikesAssertLimitOfLiabilityChangesForDifferentSituationsofIsVehicleRegisteredActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new TrailBikesAssertForIsRegisteredCausesErrorWhenChangedBackToDefaultActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new
            TrailBikesAssertForVehicleValueWhenDeletedCausesErrorActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new
            TrailBikesAssertForRegistrationNumberIsMandatoryWhenIsRegisteredSetToYesActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new
            TrailBikesAssertForVINIsMandatoryWhenVehicleIsRegisteredActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new
            TrailBikesAssertForGaragePostCodeCausesMandatoryErrorWhenDeletedActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new ExerciseInterestedPartyGridActivity< FarmMotorTrailBikesPage >
            ( vehiclePage ) );
        vehiclePage =
            performActivity( new ExerciseAccessoriesAndModificationsGridActivity< FarmMotorTrailBikesPage >(
                vehiclePage ) );
    }
}
