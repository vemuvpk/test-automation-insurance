package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualPremiumActivity;
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
@Scenario( "Change Net Of Commission And Stamp Duty For Policy With Combination of Vehicles And Override Premium Scenario" )
public class OverridePremiumWithStampDutyAndNetOfCommissionScenario extends
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
            performActivity( new CreatePrivateMotorVehicleWithManualPremiumActivity(
                insuranceHistoryPage ) );
        vehiclePage.getNavigationTree().navigateToFinish();

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        verifyNetOfCommissionAppliesToAllVehicles( premiumPage );
        verifyStampDutyExemptionAppliesToAllVehicles( premiumPage );
        verifyOverrideAppliesToAutoRatedVehiclesOnly( premiumPage );

    }

    private void verifyNetOfCommissionAppliesToAllVehicles( PremiumPage premiumPage )
    {
        // VERIFY(31.1) - Change net of Commission in premium page to YES and assert that premium
        // values are changed for both the vehicles and check commissions are 0.00

        // Get the original premium values
        Premium autoRatedVehiclePremium = premiumPage.getPremiumForRow( 1 );
        Premium manuallyVehiclePremium = premiumPage.getPremiumForRow( 2 );

        // Apply the change
        premiumPage.setNetOfCommission( "true" );

        // Get the new premium values
        Premium autoRatedVehiclePremiumNetOfCommission = premiumPage.getPremiumForRow( 1 );
        Premium manuallyVehiclePremiumNetOfCommission = premiumPage.getPremiumForRow( 2 );

        // Values for both automatic and manual vehicles should be decreased
        PremiumAssert.assertTotalPremiumDecreased( autoRatedVehiclePremium,
            autoRatedVehiclePremiumNetOfCommission );
        PremiumAssert.assertTotalPremiumUnchanged( manuallyVehiclePremium,
            manuallyVehiclePremiumNetOfCommission );

        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );
        PremiumAssert.assertZeroCommission( premiumPage );
        PremiumAssert.assertZeroCommissionGst( premiumPage );

    }

    private void verifyStampDutyExemptionAppliesToAllVehicles( PremiumPage premiumPage )
    {
        // VERIFY (31.2) - Change Is the Policy to be Exempt from Stamp Duty to Yes in Policy
        // Details page - assert for Stamp Duty value for vehicles in premium page

        // Get the original premium values
        Premium autoRatedVehiclePremium = premiumPage.getPremiumForRow( 1 );
        Premium manuallyVehiclePremium = premiumPage.getPremiumForRow( 2 );

        // Change stamp duty exemption
        PolicyDetailPage policyDetailPage =
            premiumPage.getNavigationTree().navigateToPolicyDetails();
        policyDetailPage.setExemptFromStampDuty( "true" );
        premiumPage = performActivity( new ViewPremiumActivity( policyDetailPage ) );

        // Get the new values
        Premium autoRatedVehiclePremiumAfterStampDutyExemption = premiumPage.getPremiumForRow( 1 );
        Premium manuallyVehiclePremiumAfterStampDutyExemption = premiumPage.getPremiumForRow( 2 );

        // assert that both vehicles have no stamp duty, and that the total premium decreases
        PremiumAssert.assertStampDutyExemption( autoRatedVehiclePremium,
            autoRatedVehiclePremiumAfterStampDutyExemption );
        PremiumAssert.assertStampDutyExemption( manuallyVehiclePremium,
            manuallyVehiclePremiumAfterStampDutyExemption );

        PremiumAssert.assertTotalPremiumDecreased( autoRatedVehiclePremium,
            autoRatedVehiclePremiumAfterStampDutyExemption );
        PremiumAssert.assertTotalPremiumDecreased( manuallyVehiclePremium,
            manuallyVehiclePremiumAfterStampDutyExemption );

        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );
        PremiumAssert.assertZeroCommission( premiumPage );
        PremiumAssert.assertZeroCommissionGst( premiumPage );

    }

    public void verifyOverrideAppliesToAutoRatedVehiclesOnly( PremiumPage premiumPage )
    {
        final String expectedPremiumAdjustmentAmount = "$0.34";

        // VERIFY (31.3) - Override premium and assert no change in values for manually rated
        // vehicle, stamp duty and commissions

        // Get the original premium values
        Premium autoRatedVehiclePremium = premiumPage.getPremiumForRow( 1 );
        Premium manuallyVehiclePremium = premiumPage.getPremiumForRow( 2 );

        // Apply a premium override of $0.34
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverridePremiumActivity( premiumPage,
                policyTotalPremium.add(
                    NumberConverter.convertCurrencyToBigDecimal( expectedPremiumAdjustmentAmount ) )
                    .toString() ) );

        // Get the new values
        Premium autoRatedVehiclePremiumAfterOverride = premiumPage.getPremiumForRow( 1 );
        Premium manuallyVehiclePremiumAfterOverride = premiumPage.getPremiumForRow( 2 );
        BigDecimal policyTotalPremiumAfterOverride = premiumPage.getPolicyTotalPremium();

        // make sure the override doesn't remove stamp duty exemption
        PremiumAssert.assertStampDutyExemption( autoRatedVehiclePremium,
            autoRatedVehiclePremiumAfterOverride );
        PremiumAssert.assertStampDutyExemption( manuallyVehiclePremium,
            manuallyVehiclePremiumAfterOverride );

        // The override should only apply to automatically rated vehicles
        PremiumAssert.assertTotalPremiumIncreased( autoRatedVehiclePremium,
            autoRatedVehiclePremiumAfterOverride );
        PremiumAssert.assertTotalPremiumUnchanged( manuallyVehiclePremium,
            manuallyVehiclePremiumAfterOverride );

        PremiumAssert.assertAmountCalculatedCorrectly( expectedPremiumAdjustmentAmount,
            premiumPage.getPremiumAdjustmentAmount() );
        PremiumAssert.assertZeroCommission( premiumPage );
        PremiumAssert.assertZeroCommissionGst( premiumPage );
    }
}
