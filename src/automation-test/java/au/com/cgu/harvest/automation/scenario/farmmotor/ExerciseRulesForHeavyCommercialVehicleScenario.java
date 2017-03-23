package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseGlassesGuideAccessoriesAndModificationsGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseInterestedPartyGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseMandatoryRulesForHeavyCommercialVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseReferralRulesForHeavyCommercialVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.HeavyCommercialVehicleAssertLimitOfLiabilityWhenVehicleIsNotRegisteredActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.HeavyCommercialVehicleAssertLimitOfLiabilityWhenVehicleIsRegisteredActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.HeavyCommercialVehicleAssertRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.HeavyCommercialVehicleAssertYearOfBirthOfUsualDriverIsMandatoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Declined Rules for Heavy Commercial Vehicle" )
public class ExerciseRulesForHeavyCommercialVehicleScenario extends AbstractScenario
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

        HeavyCommercialVehiclePage vehiclePage =
            performActivity( new ExerciseMandatoryRulesForHeavyCommercialVehicleActivity(
                insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new ExerciseReferralRulesForHeavyCommercialVehicleActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new HeavyCommercialVehicleAssertLimitOfLiabilityWhenVehicleIsRegisteredActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new HeavyCommercialVehicleAssertLimitOfLiabilityWhenVehicleIsNotRegisteredActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new HeavyCommercialVehicleAssertRegistrationNumberIsMandatoryWhenVehicleIsSetToRegisteredActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new HeavyCommercialVehicleAssertYearOfBirthOfUsualDriverIsMandatoryActivity(
                vehiclePage ) );

        vehiclePage =
            performActivity( new ExerciseInterestedPartyGridActivity< HeavyCommercialVehiclePage >
            ( vehiclePage ) );
        vehiclePage =
            performActivity( new ExerciseGlassesGuideAccessoriesAndModificationsGridActivity< HeavyCommercialVehiclePage >
            ( vehiclePage ) );
    }

}
