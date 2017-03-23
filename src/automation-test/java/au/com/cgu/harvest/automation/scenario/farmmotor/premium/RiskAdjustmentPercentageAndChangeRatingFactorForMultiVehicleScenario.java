package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtRiskLevelActivity;
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
@Scenario( "To Verify Adjustment Percentage at risk Level is retained after changing the rating Factor for an Auto Rated Vehicle When both Manually and Auto Rated Vehicles are Present" )
public class RiskAdjustmentPercentageAndChangeRatingFactorForMultiVehicleScenario extends
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
        // VERIFY (76.1) - Apply Adjustment percentage at Risk level to the vehicle and check the
        // calculations for Auto rated vehicle
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        verifyPositiveRiskLevelAdjustmentPercentageToAVehicle( premiumPage );

        Premium autoRatedVehicle = premiumPage.getPremiumForRow( 1 );
        Premium manuallyRatedVehicle = premiumPage.getPremiumForRow( 2 );

        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );
        verifyZeroPolicyAdjustmentPercentage( premiumPage );

        // VERIFY (76.2) - Change the Rating factor and verify that the Adj% is still retained and
        // has no impact on manually rated vehicle

        // Premium riskAdj =
        // premiumPage.getAdjustmentPercentageForRow( 1 ).getAdjustmentPercentage();

        vehiclePage =
            performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 1 ) );
        vehiclePage.setTypeOfCover( "Fire, Theft and Third Party Property Damage" );
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium autoRatedVehicleAfterRatingFactorChange = premiumPage.getPremiumForRow( 1 );
        Premium manuallyRatedVehicleAfterRatingFactorChange = premiumPage.getPremiumForRow( 2 );

        // Premium riskAdjAfterRatingfactor =
        // premiumPage.getAdjustmentPercentageForRow( 1 ).getAdjustmentPercentage();

        // Assert decrease in premium value for Auto Rated vehicle and no change in premium for
        // manually rated Vehicle
        PremiumAssert.assertTotalPremiumDecreased( autoRatedVehicle,
            autoRatedVehicleAfterRatingFactorChange );
        PremiumAssert.assertTotalPremiumUnchanged( manuallyRatedVehicle,
            manuallyRatedVehicleAfterRatingFactorChange );

        // PremiumAssert.assertRiskAdjustmentPercentagedUnchanged( riskAdj, riskAdjAfterRatingfactor
        // );

        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );
        verifyZeroPolicyAdjustmentPercentage( premiumPage );

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

    private void verifyNonZeroCommissionAppliesToAllVehicles( PremiumPage premiumPage )
    {
        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );

    }

    private void verifyZeroPolicyAdjustmentPercentage( PremiumPage premiumPage )
    {
        PremiumAssert.assertZeroPolicyAdjustmentPercentage( premiumPage );
    }

}
