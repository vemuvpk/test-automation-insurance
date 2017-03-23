package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateFarmMotorTrailerActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateHeavyCommercialVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateNonArticulatedTrailerActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateTrailBikesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateVehicleUnder2TonnesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ExerciseVehicleSummaryGridActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuredDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndAcceptActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.InsuredDetailsPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.NonArticulatedTrailersPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Issue a policy containing one of each vehicle type" )
public class AddMultipleVehiclesToIssuePolicyScenario extends AbstractScenario
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
            performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );

        AgriculturalVehiclePage agriculturalVehiclePage =
            performActivity( new CreateAgriculturalVehicleActivity( vehiclePage ) );

        HeavyCommercialVehiclePage heavyCommercialVehiclePage =
            performActivity( new CreateHeavyCommercialVehicleActivity( agriculturalVehiclePage ) );

        VehicleUnder2TonnesPage vehicleUnder2TonnesPage =
            performActivity( new CreateVehicleUnder2TonnesActivity( heavyCommercialVehiclePage ) );

        FarmMotorTrailerPage farmMotorTrailerPage =
            performActivity( new CreateFarmMotorTrailerActivity( vehicleUnder2TonnesPage ) );

        FarmMotorTrailBikesPage farMotorTrailBikesPage =
            performActivity( new CreateTrailBikesActivity( farmMotorTrailerPage ) );

        NonArticulatedTrailersPage nonArticulatedTrailersPage =
            performActivity( new CreateNonArticulatedTrailerActivity( farMotorTrailBikesPage ) );
        VehicleSummaryPage vehicleSummaryPage =
            performActivity( new ExerciseVehicleSummaryGridActivity( vehiclePage ) );

        InsuredDetailsPage insuredDetailsPage =
            performActivity( new InsuredDetailsActivity( vehicleSummaryPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyActivity( insuredDetailsPage ) );

        newBusinessPage =
            performActivity( new SaveAndAcceptActivity( newBusinessPage ) );

    }

}
