package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
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
@Scenario( "Override Premium for policy with one Manually rated vehicle and one Autorated vehicle and assert that there is no change for premium in Manually rated Vehicle" )
public class OverridePremiumAndChangeRatingFactorOfManualVehicleScenario
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
            performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new CreatePrivateMotorVehicleWithManualPremiumActivity(
                insuranceHistoryPage ) );
        // VERIFY (22.1) - override Premium and check that there is no change of values for manually
        // rated vehicle
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        // Get original premium values prior to changing rating factor
        Premium manuallyVehiclePremium = premiumPage.getPremiumForRow( 2 );
        String originalCommission = premiumPage.getCommission();
        String originalCommissionGst = premiumPage.getCommissionGst();

        verifyOverrideAppliesToAutoRatedVehiclesOnly( premiumPage );
        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );

        // VERIFY(23.2) - Change rating Factor for manually rated vehicle and check that the
        // override is reset

        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 2 ) );
        vehiclePage.setTypeOfCover( "Fire, Theft and Third Party Property Damage" );
        vehiclePage.setStandardExcess( "100.00" );
        vehiclePage.setBasePremium( "555.55" );
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium manuallyVehiclePremiumAfterRatingFactorChange = premiumPage.getPremiumForRow( 2 );
        String commissionAfterRatingFactorChange = premiumPage.getCommission();
        String commissionGstAfterRatingFactorChange = premiumPage.getCommissionGst();

        // Assert total premiums decreased with change in rating Factor
        PremiumAssert.assertTotalPremiumDecreased( manuallyVehiclePremium,
            manuallyVehiclePremiumAfterRatingFactorChange );

        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );
        PremiumAssert.assertLessThan( "Commission", commissionAfterRatingFactorChange,
            originalCommission );
        PremiumAssert.assertLessThan( "Commission GST", commissionGstAfterRatingFactorChange,
            originalCommissionGst );

    }

    private void verifyOverrideAppliesToAutoRatedVehiclesOnly( PremiumPage premiumPage )
    {
        final String expectedPremiumAdjustmentAmount = "-$0.22";

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

        // The override should only apply to automatically rated vehicles
        PremiumAssert.assertTotalPremiumDecreased( autoRatedVehiclePremium,
            autoRatedVehiclePremiumAfterOverride );
        PremiumAssert.assertTotalPremiumUnchanged( manuallyVehiclePremium,
            manuallyVehiclePremiumAfterOverride );

        PremiumAssert.assertAmountCalculatedCorrectly( expectedPremiumAdjustmentAmount,
            premiumPage.getPremiumAdjustmentAmount() );
    }

    private void verifyNonZeroCommissionAppliesToAllVehicles( PremiumPage premiumPage )
    {
        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );

    }

}
