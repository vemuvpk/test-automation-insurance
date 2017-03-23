package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.TestConfiguration;
import au.com.cgu.harvest.automation.activity.farmmotor.CopyPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateFarmMotorTrailerActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateHeavyCommercialVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateNonArticulatedTrailerActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateTrailBikesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateVehicleUnder2TonnesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuredDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.CopyPolicyPopup;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.InsuredDetailsPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.NonArticulatedTrailersPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPage;
import au.com.cgu.harvest.pages.sunrise.LoginPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Ignore
@RunWith( ParallelScenarioRunner.class )
@Scenario( "Issue a policy containing one of each vehicle type" )
public class Add50VehiclesToIssuePolicyScenario extends AbstractScenario
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

        for ( int i = 0; i < 4; i++ )
        {
            PrivateMotorVehiclePage vehiclePage =
                performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );

            AgriculturalVehiclePage agriculturalVehiclePage =
                performActivity( new CreateAgriculturalVehicleActivity( vehiclePage ) );

            HeavyCommercialVehiclePage heavyCommercialVehiclePage =
                performActivity( new CreateHeavyCommercialVehicleActivity( agriculturalVehiclePage )
                );

            VehicleUnder2TonnesPage vehicleUnder2TonnesPage =
                performActivity( new CreateVehicleUnder2TonnesActivity( heavyCommercialVehiclePage )
                );

            FarmMotorTrailerPage farmMotorTrailerPage =
                performActivity( new CreateFarmMotorTrailerActivity( vehicleUnder2TonnesPage ) );

            FarmMotorTrailBikesPage farMotorTrailBikesPage =
                performActivity( new CreateTrailBikesActivity( farmMotorTrailerPage ) );

            NonArticulatedTrailersPage nonArticulatedTrailersPage =
                performActivity( new CreateNonArticulatedTrailerActivity( farMotorTrailBikesPage ) );
        }

        InsuredDetailsPage insuredDetailsPage =
            performActivity( new InsuredDetailsActivity( insuranceHistoryPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity(
                insuranceHistoryPage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        //
        // for ( int j = 0; j < 15; j++ )
        // {
        // newBusinessPage =
        // performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        //
        // String policyNumber = newBusinessPage.getPolicyNumber();
        // // copyPolicyActivity( welcomePage, newBusinessPage );
        // policyDetailPage =
        // performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        //
        // PremiumPage premiumPage = performActivity( new ViewPremiumActivity( policyDetailPage ) );
        //
        // AddAVehicleInEndorsementTransaction( premiumPage );
        //
        // DeleteAVehicleInEndorsementTransaction( premiumPage );
        // policyDetailPage =
        // performActivity( new NavigateToPolicyDetailPageActivity( policyDetailPage ) );
        //
        // policyDetailPage =
        // performActivity( new SetDutyOfDisclosureActivity( policyDetailPage ) );
        // insuranceHistoryPage =
        // performActivity( new SetAnsweredForAllDriversInInsuranceHistoryPageActivity(
        // policyDetailPage ) );
        // FinishPage finishPage =
        // performActivity( new NavigateToFinishPageActivity( premiumPage ) );
        // finishPage.finish();
        //
        // }

    }

    private void AddAVehicleInEndorsementTransaction( PremiumPage premiumPage )
    {
        PrivateMotorVehiclePage vehiclePage;
        vehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity(
                premiumPage ) );
    }

    private void DeleteAVehicleInEndorsementTransaction( PremiumPage premiumPage )
    {
        PrivateMotorVehiclePage vehiclePage;
        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 1 ) );
        VehicleSummaryPage vehicleSummaryPage =
            performActivity( new DeleteVehicleActivity< PrivateMotorVehiclePage >( vehiclePage ) );
    }

    private void copyPolicyActivity( WelcomePage welcomePage, NewBusinessPage newBusinessPage )
    {
        WebDriver _driver;
        PolicyDetailPage policyDetailPage;
        String policyNumber = newBusinessPage.getPolicyNumber();
        getWebDriver().quit();
        WebDriver driver = new FirefoxDriver();
        driver.get(
            TestConfiguration.getCurrentTestConfiguration().getSunriseExecutiveUrl() );

        LoginPage loginPage = new LoginPage( driver );

        loginPage.login( TestConfiguration.getCurrentTestConfiguration()
            .getIntermediaryDetails().getCompanyId(),
            TestConfiguration.getCurrentTestConfiguration()
                .getIntermediaryDetails().getUserId(),
            TestConfiguration.getCurrentTestConfiguration()
                .getIntermediaryDetails().getPassword() );
        policyDetailPage =
            performActivity( new LaunchFarmMotorActivity( welcomePage ) );
        CopyPolicyPopup copyPolicyPopup =
            performActivity( new CopyPolicyActivity( policyDetailPage ) );
        copyPolicyPopup.setPolicyNumber( policyNumber );
        copyPolicyPopup.copy();

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        InsuranceHistoryPage insuranceHistoryPage =
            performActivity( new InsuranceHistoryActivity( policyDetailPage ) );

        for ( int i = 0; i <= 3; i++ )
        {
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
        }
    }

}
