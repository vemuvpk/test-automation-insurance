package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Assert;
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
@Scenario( "Perform an override of the premium And Add Adjustment percentage at Risk Level for an auto-rated vehicle and check if Override is reset" )
public class OverrideAndAdjustAutoRatedVehicleScenario
    extends AbstractScenario
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

        // VERIFY (54.1) - Apply an override and check calculation is correct
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium autoRatedVehiclePremium = premiumPage.getPremiumForRow( 1 );
        verifyOverrideAppliesToAutoRatedVehiclesOnly( premiumPage );
        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );
        PremiumAssert.assertNonZeroPremiumAdjustmentAmount( premiumPage );

        // VERIFY (54.2) - Apply Adjustment percentage at Risk level to the vehicle and check the
        // calculations for Auto rated vehicle and chk if the premium is reset

        verifyAdjustmentPercentageAppliesToAutoRatedVehicle( premiumPage );
        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );
        assertPolicyAdjustmentPercentageCorrectly( premiumPage, "" );
        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );

    }

    public void verifyAdjustmentPercentageAppliesToAutoRatedVehicle( PremiumPage premiumPage )
    {
        final String expectedAdjustmentPercentage = "2.00";
        Premium autoRatedVehiclePremium = premiumPage.getPremiumForRow( 1 );

        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage
                    .getPremiumGrid().getPremiumAtRow( 1 ), "2.0" ) );

        // Get the new values after Adjustment Percentage
        Premium autoRatedVehiclePremiumAfterAdjustmentPercentage = premiumPage.getPremiumForRow( 1 );

        // Adj Percentage should apply for Auto Rated vehicle
        PremiumAssert.assertTotalPremiumIncreased( autoRatedVehiclePremium,
            autoRatedVehiclePremiumAfterAdjustmentPercentage );
    }

    private void verifyNonZeroCommissionAppliesToAllVehicles( PremiumPage premiumPage )
    {
        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );

    }

    public void verifyOverrideAppliesToAutoRatedVehiclesOnly( PremiumPage premiumPage )
    {
        final String expectedPremiumAdjustmentAmount = "-$0.18";

        // VERIFY (31.3) - Override premium and assert no change in values for manually rated
        // vehicle, stamp duty and commissions

        // Get the original premium values
        Premium autoRatedVehiclePremium = premiumPage.getPremiumForRow( 1 );

        // Apply a premium override of "-$0.18"
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverridePremiumActivity( premiumPage,
                policyTotalPremium.add(
                    NumberConverter.convertCurrencyToBigDecimal( expectedPremiumAdjustmentAmount ) )
                    .toString() ) );

        // Get the new values
        Premium autoRatedVehiclePremiumAfterOverride = premiumPage.getPremiumForRow( 1 );
        BigDecimal policyTotalPremiumAfterOverride = premiumPage.getPolicyTotalPremium();

        // The override should only apply to automatically rated vehicles
        PremiumAssert.assertTotalPremiumDecreased( autoRatedVehiclePremium,
            autoRatedVehiclePremiumAfterOverride );

        PremiumAssert.assertAmountCalculatedCorrectly( expectedPremiumAdjustmentAmount,
            premiumPage.getPremiumAdjustmentAmount() );
        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );
    }

    private void assertPolicyAdjustmentPercentageCorrectly( PremiumPage premiumPage,
        String expectedAmount )
    {
        Assert.assertEquals( expectedAmount, premiumPage.getPolicyAdjustmentPercentage() );
    }

}
