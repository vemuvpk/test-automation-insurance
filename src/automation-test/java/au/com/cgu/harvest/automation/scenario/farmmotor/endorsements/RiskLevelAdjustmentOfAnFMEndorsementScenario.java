package au.com.cgu.harvest.automation.scenario.farmmotor.endorsements;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateVehicleUnder2TonnesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtRiskLevelActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.VehicleDetailPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Ignore( "Cannot reproduce it manually. this happens all the time when run on the selenium server." )
@RunWith( ParallelScenarioRunner.class )
@Scenario( "Verify Risk Level Adj% for Farm Motor Endorsement Transaction Scenario" )
public class RiskLevelAdjustmentOfAnFMEndorsementScenario extends
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

        AgriculturalVehiclePage agriculturalVehiclePage =
            performActivity( new CreateAgriculturalVehicleActivity( vehiclePage ) );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( agriculturalVehiclePage ) );

        verifyPositiveRiskLevelAdjustmentPercentageToFirstVehicle( premiumPage );
        verifyNegaticeRiskLevelAdjustmentPercentageToSecondVehicle( premiumPage );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( vehiclePage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        assertRiskAdjustmentPercentage( premiumPage, "2.00", 1 );
        assertRiskAdjustmentPercentage( premiumPage, "-2.00", 2 );

        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 1 ) );
        vehiclePage.selectNonComprehensiveCover( "Fire, Theft and Third Party Property Damage" );

        agriculturalVehiclePage =
            performActivity( new EditAgriculturalVehicleActivity( vehiclePage, 2 ) );
        VehicleSummaryPage vehicleSummaryPage =
            performActivity( new DeleteVehicleActivity< VehicleDetailPage >(
                agriculturalVehiclePage ) );

        VehicleUnder2TonnesPage vehicleUnder2Tonnes =
            performActivity( new CreateVehicleUnder2TonnesActivity( vehicleSummaryPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        // TESTING:: CFMC-335

        AbstractScenario.getScenarioLogger().trace(
            "************************Raised Bug CFMC-335 ****************" );
        assertRiskAdjustmentPercentage( premiumPage, "2.00", 1 );
        assertRiskAdjustmentPercentage( premiumPage, "-2.00", 2 );
        PremiumAssert.assertZeroRiskAdjustmentPercentage( premiumPage, 3 );

    }

    private void verifyPositiveRiskLevelAdjustmentPercentageToFirstVehicle(
        PremiumPage premiumPage )
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

    private void verifyNegaticeRiskLevelAdjustmentPercentageToSecondVehicle( PremiumPage premiumPage )
    {
        final String expectedPolicyLevelAdjustmentPercentage = "-2.0";

        // Get the original values before Adjustment percentage
        Premium autoRatedSecondVehicle = premiumPage.getPremiumForRow( 2 );

        // Apply Policy Level Adjustment Percentage
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 2 ),
                expectedPolicyLevelAdjustmentPercentage ) );

        Premium autoRatedVehicleAfterAdjPercentage = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumDecreased( autoRatedSecondVehicle,
            autoRatedVehicleAfterAdjPercentage );
    }

    public static void assertRiskAdjustmentPercentage( PremiumPage premiumPage,
        String riskAdjustment, int rowNumber )
    {
        Assert.assertEquals( new BigDecimal( riskAdjustment ),
            premiumPage.getPremiumForRow( rowNumber ).getAdjustmentPercentage() );
    }

}
