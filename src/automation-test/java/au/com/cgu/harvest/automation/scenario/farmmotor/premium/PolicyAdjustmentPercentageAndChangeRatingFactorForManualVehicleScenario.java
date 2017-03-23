package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtPolicyLevelActivity;
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
@Scenario( "To Check Adjustment Percentage has no impact when Combination of vehicles are present and Rating factor for manually rated vehicle is changed." )
public class PolicyAdjustmentPercentageAndChangeRatingFactorForManualVehicleScenario
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
            performActivity( new CreatePrivateMotorVehicleWithManualPremiumActivity(
                insuranceHistoryPage ) );

        // VERIFY (74.1) - Apply Policy Adjustment percentage - positive value and check the values
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium autoRatedVehicle = premiumPage.getPremiumForRow( 1 );
        Premium manuallyRatedVehicle = premiumPage.getPremiumForRow( 2 );
        verifyPositiveAdjustmentPercentagePolicyLevel( premiumPage, "5.5" );
        Premium autoRatedVehicleAfterPolicyAdjustment = premiumPage.getPremiumForRow( 1 );
        Premium manuallyRatedVehicleAfterPolicyLevelAdj = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertNonZeroPolicyAdjustmentPercentage( premiumPage );

        // VERIFY(74.2) - Change rating Factor for manually rated vehicle and check that there is no
        // impact on Auto Rated Vehicle
        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 2 ) );
        vehiclePage.setTypeOfCover( "Fire, Theft and Third Party Property Damage" );

        vehiclePage.setStandardExcess( "100.00" );
        vehiclePage.setBasePremium( "555.55" );
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium autoRatedVehicleAfterRatingfactorChange = premiumPage.getPremiumForRow( 1 );
        Premium manuallyRatedVehicleAfterRatingfactorChange = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumUnchanged( autoRatedVehicleAfterPolicyAdjustment,
            autoRatedVehicleAfterRatingfactorChange );

        PremiumAssert.assertNonZeroPolicyAdjustmentPercentage( premiumPage );
        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );
    }

    private void verifyPositiveAdjustmentPercentagePolicyLevel( PremiumPage premiumPage,
        String policyLevelAdjPercentage )
    {
        Premium autoRatedFirstVehicle = premiumPage.getPremiumForRow( 1 );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                policyLevelAdjPercentage ) );
        Premium autoRatedFirstVehicleAfterPolicyLevelAdj = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumIncreased( autoRatedFirstVehicle,
            autoRatedFirstVehicleAfterPolicyLevelAdj );
        BigDecimal policyTotalPremiumAfterAdjustmentPercentage =
            premiumPage.getPolicyTotalPremium();

    }
}
