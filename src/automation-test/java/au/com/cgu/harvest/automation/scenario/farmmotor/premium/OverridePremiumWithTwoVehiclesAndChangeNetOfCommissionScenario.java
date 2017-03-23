package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithNoWindscreenExtensionActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
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
@Scenario( "Perform an override of the premium for Two auto-rated vehicles and check for premium after setting net of commission to YES" )
public class OverridePremiumWithTwoVehiclesAndChangeNetOfCommissionScenario extends
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
            performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new CreatePrivateMotorVehicleWithNoWindscreenExtensionActivity(
                insuranceHistoryPage ) );

        // VERIFY (13.1) - Apply an override and check calculation is correct
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        verifyOverrideAppliesToAutoRatedVehiclesOnly( premiumPage );
        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );

        PremiumAssert.assertNonZeroPremiumAdjustmentAmount( premiumPage );
        // assertOverrideAmountCalculatedCorrectly( premiumPage, "-$0.31" );

        Premium autoRatedFirstVehicle = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehicle = premiumPage.getPremiumForRow( 2 );

        // VERIFY (13.2) - Verify premium is reset after Changing Net of Commission to Yes
        premiumPage.setNetOfCommission( "true" );

        Premium autoRatedFirstVehicleAfterNetOfCommission = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehicleAfterNetOfCommission = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumDecreased( autoRatedFirstVehicle,
            autoRatedFirstVehicleAfterNetOfCommission );
        PremiumAssert.assertTotalPremiumDecreased( autoRatedSecondVehicle,
            autoRatedSecondVehicleAfterNetOfCommission );

        // assertOverrideAmountCalculatedCorrectly( premiumPage, "$0.00" );
        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );
        verifyZeroCommissionAppliesToAllVehicles( premiumPage );

    }

    private void verifyOverrideAppliesToAutoRatedVehiclesOnly( PremiumPage premiumPage )
    {
        final String expectedPremiumAdjustmentAmount = "-$0.31";

        // VERIFY (31.3) - Override premium and assert no change in values for manually rated
        // vehicle, stamp duty and commissions

        // Get the original premium values
        Premium autoRatedVehiclePremium = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedVehiclePremiumWithNoWindscreen = premiumPage.getPremiumForRow( 2 );

        // Apply a premium override of $0.34
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverridePremiumActivity( premiumPage,
                policyTotalPremium.add(
                    NumberConverter.convertCurrencyToBigDecimal( expectedPremiumAdjustmentAmount ) )
                    .toString() ) );

        // Get the new values
        Premium autoRatedVehiclePremiumAfterOverride = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedVehiclePremiumWithNoWindscreenAfterOverride =
            premiumPage.getPremiumForRow( 2 );
        BigDecimal policyTotalPremiumAfterOverride = premiumPage.getPolicyTotalPremium();

        // The override should only apply to automatically rated vehicles
        PremiumAssert.assertTotalPremiumDecreased( autoRatedVehiclePremium,
            autoRatedVehiclePremiumAfterOverride );
        PremiumAssert.assertTotalPremiumDecreased( autoRatedVehiclePremiumWithNoWindscreen,
            autoRatedVehiclePremiumWithNoWindscreenAfterOverride );

        PremiumAssert.assertAmountCalculatedCorrectly( expectedPremiumAdjustmentAmount,
            premiumPage.getPremiumAdjustmentAmount() );
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
