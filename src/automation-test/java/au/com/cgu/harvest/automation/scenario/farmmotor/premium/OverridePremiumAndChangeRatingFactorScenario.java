package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverridePremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.NumberConverter;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Override Premium for policy with one Manually rated vehicle and one Autorated vehicle and assert that there is no change for premium in Manually rated Vehicle" )
public class OverridePremiumAndChangeRatingFactorScenario extends
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

        // VERIFY (25.1) - override Premium and check that there is no change of values for manually
        // rated vehicle
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium autoRatedVehicle = premiumPage.getPremiumForRow( 1 );
        Premium manuallyRatedVehicle = premiumPage.getPremiumForRow( 2 );

        verifyOverrideAppliesToAutoRatedVehiclesOnly( premiumPage );
        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );
        PremiumAssert.assertNonZeroPremiumAdjustmentAmount( premiumPage );

        // VERIFY(25.2) - Change rating Factor for Auto rated vehicle and check that the
        // override is reset

        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 1 ) );
        vehiclePage.setTypeOfCover( "Fire, Theft and Third Party Property Damage" );
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );

        // Suspend, save and edit the policy
        NewBusinessPage newBusinessPage = performActivity( new SuspendActivity( premiumPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        // VERIFY (1.2) - Verify no changes after resuming from suspend
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );
    }

    public void verifyOverrideAppliesToAutoRatedVehiclesOnly( PremiumPage premiumPage )
    {
        final String expectedPremiumAdjustmentAmount = "$32.20";

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
        PremiumAssert.assertTotalPremiumIncreased( autoRatedVehiclePremium,
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

    private void verifyNonZeroCommissionAppliesToAllVehicles( PremiumPage premiumPage )
    {
        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );
    }
}
