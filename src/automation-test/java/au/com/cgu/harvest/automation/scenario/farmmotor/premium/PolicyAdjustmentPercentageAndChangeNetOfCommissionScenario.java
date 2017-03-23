package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtPolicyLevelActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "To Check Adjustment percentage and commissions are calculated correctly when combination of vehicles are present and when net of commission is set to TRUE" )
public class PolicyAdjustmentPercentageAndChangeNetOfCommissionScenario extends
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

        PrivateMotorVehiclePage vehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity(
                insuranceHistoryPage ) );
        vehiclePage =
            performActivity( new CreatePrivateMotorVehicleWithManualPremiumActivity(
                insuranceHistoryPage ) );
        vehiclePage.getNavigationTree().navigateToFinish();
        // VERIFY (73.1) - Apply Policy Adjustment percentage - positive value and check the values

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        Premium autoRatedVehicle = premiumPage.getPremiumForRow( 1 );
        Premium manuallyRatedVehicle = premiumPage.getPremiumForRow( 2 );
        verifyPositiveAdjustmentPercentagePolicyLevel( premiumPage, "5.5" );
        Premium autoRatedVehicleAfterPolicyAdjustment = premiumPage.getPremiumForRow( 1 );
        Premium manuallyRatedVehicleAfterPolicyLevelAdj = premiumPage.getPremiumForRow( 2 );
        PremiumAssert.assertNonZeroPolicyAdjustmentPercentage( premiumPage );

        // VERIFY (73.2) - Change net of commission and check the values
        premiumPage.setNetOfCommission( "true" );

        Premium autoRatedVehicleAfterNetOfCommission = premiumPage.getPremiumForRow( 1 );
        Premium manuallyRatedVehicleAfterNetOfCommission = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumDecreased( autoRatedVehicleAfterPolicyAdjustment,
            autoRatedVehicleAfterNetOfCommission );
        PremiumAssert.assertTotalPremiumDecreased( autoRatedVehicleAfterPolicyAdjustment,
            manuallyRatedVehicleAfterNetOfCommission );

        PremiumAssert.assertNonZeroPolicyAdjustmentPercentage( premiumPage );
        PremiumAssert.assertZeroCommission( premiumPage );
        PremiumAssert.assertZeroCommissionGst( premiumPage );
    }

    private void verifyPositiveAdjustmentPercentagePolicyLevel( PremiumPage premiumPage,
        String policyLevelAdjPercentage )
    {
        Premium autoRatedFirstVehicle = premiumPage.getPremiumForRow( 1 );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                policyLevelAdjPercentage ) );
        Premium autoRatedFirstVehicleAfterPolicyLevelAdj = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumIncreased( autoRatedFirstVehicle,
            autoRatedFirstVehicleAfterPolicyLevelAdj );
        BigDecimal policyTotalPremiumAfterAdjustmentPercentage =
            premiumPage.getPolicyTotalPremium();

    }
}
