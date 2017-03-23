package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertPremiumValuesInReadOnlyPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuredDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFarmmotorInsuredDetailsPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFirstVehiclePageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToInsuranceHistoryPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ClosePolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndAcceptActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.InsuredDetailsPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.ReadOnlyChecker;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert all fields are in Read Only after the policy is closed in FarmMotor scenario" )
public class AssertReadOnlyPagesForFarmMotorPolicyScenario extends AbstractScenario
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

        InsuredDetailsPage insuredDetailsPage =
            performActivity( new InsuredDetailsActivity( vehiclePage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity(
                insuredDetailsPage ) );

        newBusinessPage =
            performActivity( new SaveAndAcceptActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ClosePolicyActivity( newBusinessPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), policyDetailPage );

        insuranceHistoryPage =
            performActivity( new NavigateToInsuranceHistoryPageActivity( policyDetailPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), insuranceHistoryPage );

        vehiclePage =
            performActivity( new NavigateToFirstVehiclePageActivity( insuranceHistoryPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), vehiclePage );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( vehiclePage ) );

        premiumPage =
            performActivity( new AssertPremiumValuesInReadOnlyPageActivity( premiumPage ) );

        insuredDetailsPage =
            performActivity( new NavigateToFarmmotorInsuredDetailsPageActivity(
                premiumPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), insuredDetailsPage );
    }

}
