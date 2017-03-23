package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverridePremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ResetPremiumActivity;
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
@Scenario( "Perform an override of the premium for an auto-rated vehicle" )
public class OverridePremiumWithAutoRatedVehicleScenario extends AbstractScenario
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

        // VERIFY (1.7) - Apply an override and check calculation is correct
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        verifyOverrideAppliesToAutoRatedVehiclesOnly( premiumPage );
        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );

        Premium autoRatedVehiclePremiumAfterOverride = premiumPage.getPremiumForRow( 1 );

        // Suspend, Save and edit the policy
        NewBusinessPage newBusinessPage = performActivity( new SuspendActivity( premiumPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        // VERIFY (1.2) - Verify no changes after resuming from suspend
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        Premium autoRatedVehiclePremiumAfterOverrideAfterSuspend = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumUnchanged( autoRatedVehiclePremiumAfterOverride,
            autoRatedVehiclePremiumAfterOverrideAfterSuspend );

        // VERIFY (1.3) - Reset
        premiumPage = performActivity( new ResetPremiumActivity( premiumPage ) );

        // VERIFY (1.8) - Override premium then change a rating factor
        verifyOverrideAppliesToAutoRatedVehiclesOnly( premiumPage );

        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 1 ) );
        vehiclePage.setTypeOfCover( "Fire, Theft and Third Party Property Damage" );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium AutoRatedVehiclePremiumAfterratingFactorChange = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumDecreased(
            autoRatedVehiclePremiumAfterOverrideAfterSuspend,
            AutoRatedVehiclePremiumAfterratingFactorChange );
        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );

    }

    private void verifyOverrideAppliesToAutoRatedVehiclesOnly( PremiumPage premiumPage )
    {
        final String expectedPremiumAdjustmentAmount = "-$0.18";

        // VERIFY (31.3) - Override premium and assert no change in values for manually rated
        // vehicle, stamp duty and commissions

        // Get the original premium values
        Premium autoRatedVehiclePremium = premiumPage.getPremiumForRow( 1 );

        // Apply a premium override of -$0.18
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
    }

    private void verifyNonZeroCommissionAppliesToAllVehicles( PremiumPage premiumPage )
    {
        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );
    }

}
