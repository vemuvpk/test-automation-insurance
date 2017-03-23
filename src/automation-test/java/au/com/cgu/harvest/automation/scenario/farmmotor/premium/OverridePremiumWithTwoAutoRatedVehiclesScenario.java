package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithNoWindscreenExtensionActivity;
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
public class OverridePremiumWithTwoAutoRatedVehiclesScenario extends AbstractScenario
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

        // VERIFY (8.1) - Apply an override and check calculation is correct
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        verifyOverrideAppliesAndCheckNonZeroCommissions( premiumPage );
        Premium autoRatedVehicle = premiumPage.getPremiumForRow( 1 );
        Premium autoRtedVehicleWithNoWindscreen = premiumPage.getPremiumForRow( 2 );

        // Suspend, Save and edit Policy
        NewBusinessPage newBusinessPage = performActivity( new SuspendActivity( premiumPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        // VERIFY (8.2) - Verify no changes after resuming from suspend
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        Premium autoRatedVehicleAfterSuspend = premiumPage.getPremiumForRow( 1 );
        Premium autoRtedVehicleWithNoWindscreenAfterSuspend = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumUnchanged( autoRatedVehicle, autoRatedVehicleAfterSuspend );
        PremiumAssert.assertTotalPremiumUnchanged( autoRtedVehicleWithNoWindscreen,
            autoRtedVehicleWithNoWindscreenAfterSuspend );

        // VERIFY (8.5) - Reset
        premiumPage = performActivity( new ResetPremiumActivity( premiumPage ) );

        // VERIFY (8.3) - Override premium then change a rating factor for second Vehicle
        premiumPage =
            overridePremiumAndChangeRatingFactorForSecondVehicle( premiumPage,
                autoRtedVehicleWithNoWindscreenAfterSuspend );

        // VERIFY (8.4) - change a non rating factor for second Vehicle
        premiumPage =
            changeNonRatingFieldOnSecondVehicleAndAssertPremiumValuesAreUnchanged( premiumPage );

        // VERIFY (8.5) - Reset
        premiumPage = performActivity( new ResetPremiumActivity( premiumPage ) );

        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );

    }

    private PremiumPage changeNonRatingFieldOnSecondVehicleAndAssertPremiumValuesAreUnchanged(
        PremiumPage premiumPage )
    {
        PrivateMotorVehiclePage vehiclePage;
        verifyOverrideAppliesAndCheckNonZeroCommissions( premiumPage );
        Premium autoRatedVehicleAfterSecondOverride = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedVehicleWithNoWindScreenAfterSecondOverride =
            premiumPage.getPremiumForRow( 2 );

        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 2 ) );
        vehiclePage.setRegistrationNumber( "1111" );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium autoRatedVehicleAfterNonRatingFactorChanged = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedVehicleWithNoWindScreenAfterNonRatingFactorChanged =
            premiumPage.getPremiumForRow( 2 );

        // Verify that there is no change in premium values after change of non rating factor field
        PremiumAssert.assertTotalPremiumUnchanged( autoRatedVehicleAfterSecondOverride,
            autoRatedVehicleAfterNonRatingFactorChanged );
        PremiumAssert.assertTotalPremiumUnchanged(
            autoRatedVehicleWithNoWindScreenAfterSecondOverride,
            autoRatedVehicleWithNoWindScreenAfterNonRatingFactorChanged );
        return premiumPage;
    }

    private PremiumPage overridePremiumAndChangeRatingFactorForSecondVehicle(
        PremiumPage premiumPage, Premium autoRtedVehicleWithNoWindscreenAfterSuspend )
    {
        PrivateMotorVehiclePage vehiclePage;
        verifyOverrideAppliesAndCheckNonZeroCommissions( premiumPage );

        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 2 ) );
        vehiclePage.setTypeOfCover( "Fire, Theft and Third Party Property Damage" );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        Premium autoRtedVehicleWithNoWindscreenAfterratingFactorChange =
            premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumDecreased( autoRtedVehicleWithNoWindscreenAfterSuspend,
            autoRtedVehicleWithNoWindscreenAfterratingFactorChange );
        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );
        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );
        return premiumPage;
    }

    private void verifyOverrideAppliesAndCheckNonZeroCommissions( PremiumPage premiumPage )
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

        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );
    }

    private void verifyNonZeroCommissionAppliesToAllVehicles( PremiumPage premiumPage )
    {
        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );

    }

}
