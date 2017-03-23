package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
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
@Scenario( "To Check adjustment percentage for Two rated vehicles for both Positve and Negative values" )
public class PolicyAdjustmentPercentageForTwoAutoRatedVehicleScenario extends AbstractScenario
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
            performActivity( new CreatePrivateMotorVehicleActivity(
                insuranceHistoryPage ) );

        // VERIFY (61.1) - Apply Policy Adjustment percentage - positive value and check the values

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium autoRatedFirstVehicle = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehicle = premiumPage.getPremiumForRow( 2 );

        verifyPositivePolicyLevelAdjustmentPercentageAppliesToAllVehicles( premiumPage );

        // VERIFY (62.1) - Apply Policy Adjustment percentage - Negative value and check the values
        verifyNegativePolicyLevelAdjustmentPercentageAppliesToAllVehicles( premiumPage );

    }

    private void verifyPositivePolicyLevelAdjustmentPercentageAppliesToAllVehicles(
        PremiumPage premiumPage )
    {
        final String expectedPolicyLevelAdjustmentPercentage = "5.5";

        // Get the original values before Adjustment percentage
        Premium autoRatedFirstVehicle = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehicle = premiumPage.getPremiumForRow( 2 );

        // Apply Policy Level Adjustment Percentage
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                expectedPolicyLevelAdjustmentPercentage ) );

        Premium autoRatedFirstVehicleAfterAdjPercentage = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehicleAfterAdjPercentage = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumIncreased( autoRatedFirstVehicle,
            autoRatedFirstVehicleAfterAdjPercentage );
        PremiumAssert.assertTotalPremiumIncreased( autoRatedSecondVehicle,
            autoRatedSecondVehicleAfterAdjPercentage );
        BigDecimal policyTotalPremiumAfterAdjustmentPercentage =
            premiumPage.getPolicyTotalPremium();
    }

    private void verifyNegativePolicyLevelAdjustmentPercentageAppliesToAllVehicles(
        PremiumPage premiumPage )
    {
        final String expectedPolicyLevelAdjustmentPercentage = "-2.5";

        // Get the original values before Adjsutment percentage
        Premium autoRatedFirstVehicle = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehicle = premiumPage.getPremiumForRow( 2 );

        // Apply Policy Level Adjustment Percentage
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                expectedPolicyLevelAdjustmentPercentage ) );

        Premium autoRatedFirstVehicleAfterAdjPercentage = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehicleAfterAdjPercentage = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumDecreased( autoRatedFirstVehicle,
            autoRatedFirstVehicleAfterAdjPercentage );
        PremiumAssert.assertTotalPremiumDecreased( autoRatedSecondVehicle,
            autoRatedSecondVehicleAfterAdjPercentage );
        BigDecimal policyTotalPremiumAfterAdjustmentPercentage =
            premiumPage.getPolicyTotalPremium();
    }

}
