package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
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
@Scenario( "To Verify Adjustment Percentage at risk Level is retained after changing the rating Factor for an Auto Rated Vehicle" )
public class RiskAdjustmentPercentageAndChangeRatingFactorScenario extends
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

        // VERIFY (52.1) - Apply Adjustment percentage at Risk level to the vehicle and check the
        // calculations for Auto rated vehicle
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        verifyPositiveRiskLevelAdjustmentPercentageToAVehicle( premiumPage );

        Premium autoRatedVehicle = premiumPage.getPremiumForRow( 1 );

        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );
        verifyZeroPolicyAdjustmentPercentage( premiumPage );

        // VERIFY (52.2) - Change the Rating factor and verify that the Adj% is still retained
        vehiclePage =
            performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 1 ) );
        vehiclePage.setTypeOfCover( "Fire, Theft and Third Party Property Damage" );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium autoRatedVehicleAfterRatingFactorChange = premiumPage.getPremiumForRow( 1 );

        // Assert decrease in premium value for Auto Rated vehicle and no change in Adj Percentage
        PremiumAssert.assertTotalPremiumDecreased( autoRatedVehicle,
            autoRatedVehicleAfterRatingFactorChange );
        PremiumAssert.assertRiskAdjustmentPercentageUnchanged( autoRatedVehicle,
            autoRatedVehicleAfterRatingFactorChange );

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
