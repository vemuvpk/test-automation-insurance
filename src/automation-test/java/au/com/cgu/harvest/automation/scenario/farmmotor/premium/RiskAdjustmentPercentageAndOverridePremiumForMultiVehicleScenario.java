package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtRiskLevelActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverridePremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.NumberConverter;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "To Check Calculation when Risk Level Adj Percentage(Both Positive and Negative) is Done and premium override and Net of commission changed for Two Auto Rated Vehicle" )
public class RiskAdjustmentPercentageAndOverridePremiumForMultiVehicleScenario
    extends
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
            performActivity( new CreatePrivateMotorVehicleActivity(
                insuranceHistoryPage ) );

        // VERIFY (69.1) - Apply Adjustment percentage - Positive Integer at Risk level to the
        // vehicle and check the calculations for Auto rated vehicle
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        verifyPositiveRiskLevelAdjustmentPercentageToAVehicle( premiumPage );
        verifyZeroPolicyAdjustmentPercentage( premiumPage );

        // VERIFY (69.2) - Apply Adjustment percentage - Negative Integer at Risk level to the
        // vehicle and check the calculations for Auto rated vehicle
        verifyNegativeRiskLevelAdjustmentPercentageToAVehicle( premiumPage );
        verifyZeroPolicyAdjustmentPercentage( premiumPage );

        // VERIFY (69.3) - Apply an Premium override and check Adjustment percentage is retained and
        // calculations are correct
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        verifyOverrideAppliesToAutoRatedVehiclesOnly( premiumPage, "$0.64" );
        PremiumAssert.assertNonZeroPremiumAdjustmentAmount( premiumPage );
        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );

        Premium riskAdjForFirstVehicleBeforeNetOfCommission = premiumPage.getPremiumForRow( 1 );
        Premium riskAdjForSecondVehicleBeforeNetOfCommission = premiumPage.getPremiumForRow( 2 );

        // VERIFY (69.4) - Change net of commission and check the values and chk Adj% is retained
        premiumPage.setNetOfCommission( "true" );

        Premium riskAdjForFirstVehicleAfterNetOfCommission =
            premiumPage.getPremiumForRow( 1 );
        Premium riskAdjForSecondVehicleAfterNetOfCommission =
            premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertRiskAdjustmentPercentageUnchanged(
            riskAdjForFirstVehicleBeforeNetOfCommission,
            riskAdjForFirstVehicleAfterNetOfCommission );
        PremiumAssert.assertRiskAdjustmentPercentageUnchanged(
            riskAdjForSecondVehicleBeforeNetOfCommission,
            riskAdjForSecondVehicleAfterNetOfCommission );

        verifyZeroCommissionAppliesToAllVehicles( premiumPage );
        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );

    }

    private void verifyPositiveRiskLevelAdjustmentPercentageToAVehicle( PremiumPage premiumPage )
    {
        final String expectedPolicyLevelAdjustmentPercentage = "2.0";

        // Get the original values before Adjustment percentage
        Premium autoRatedFirstVehicle = premiumPage.getPremiumForRow( 1 );

        // Apply Policy Level Adjustment Percentage
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 1 ),
                expectedPolicyLevelAdjustmentPercentage ) );

        Premium autoRatedVehicleAfterAdjPercentage = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumIncreased( autoRatedFirstVehicle,
            autoRatedVehicleAfterAdjPercentage );
        BigDecimal policyTotalPremiumAfterAdjustmentPercentage =
            premiumPage.getPolicyTotalPremium();
    }

    private void verifyNegativeRiskLevelAdjustmentPercentageToAVehicle( PremiumPage premiumPage )
    {
        final String expectedPolicyLevelAdjustmentPercentage = "-2.0";

        // Get the original values before Adjustment percentage
        Premium autoRatedSecondVehicle = premiumPage.getPremiumForRow( 2 );

        // Apply Policy Level Adjustment Percentage
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 2 ),
                expectedPolicyLevelAdjustmentPercentage ) );

        Premium autoRatedVehicleAfterAdjPercentage = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumDecreased( autoRatedSecondVehicle,
            autoRatedVehicleAfterAdjPercentage );
        BigDecimal policyTotalPremiumAfterAdjustmentPercentage =
            premiumPage.getPolicyTotalPremium();
    }

    private void verifyOverrideAppliesToAutoRatedVehiclesOnly( PremiumPage premiumPage,
        String expectedPremiumAdjustmentAmount )
    {
        // VERIFY (31.3) - Override premium and assert change in values for all Auto Rated Vehicles

        // Get the original premium values
        Premium autoRatedFirstVehiclePremium = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehiclePremium = premiumPage.getPremiumForRow( 2 );

        // Apply a premium override
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverridePremiumActivity( premiumPage,
                policyTotalPremium.add(
                    NumberConverter.convertCurrencyToBigDecimal( expectedPremiumAdjustmentAmount ) )
                    .toString() ) );

        // Get the new values
        Premium autoRatedFirstVehiclePremiumAfterOverride = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehiclePremiumAfterOverride = premiumPage.getPremiumForRow( 2 );
        BigDecimal policyTotalPremiumAfterOverride = premiumPage.getPolicyTotalPremium();

        // The override should only apply to automatically rated vehicles
        PremiumAssert.assertTotalPremiumIncreased( autoRatedFirstVehiclePremium,
            autoRatedFirstVehiclePremiumAfterOverride );
        PremiumAssert.assertTotalPremiumIncreased( autoRatedSecondVehiclePremium,
            autoRatedSecondVehiclePremiumAfterOverride );

        PremiumAssert.assertAmountCalculatedCorrectly( expectedPremiumAdjustmentAmount,
            premiumPage.getPremiumAdjustmentAmount() );
    }

    private void verifyZeroPolicyAdjustmentPercentage( PremiumPage premiumPage )
    {
        PremiumAssert.assertZeroPolicyAdjustmentPercentage( premiumPage );
    }

    private void verifyNonZeroCommissionAppliesToAllVehicles( PremiumPage premiumPage )
    {
        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );
    }

    private void verifyZeroCommissionAppliesToAllVehicles( PremiumPage premiumPage )
    {
        PremiumAssert.assertZeroCommission( premiumPage );
        PremiumAssert.assertZeroCommissionGst( premiumPage );
    }

}
