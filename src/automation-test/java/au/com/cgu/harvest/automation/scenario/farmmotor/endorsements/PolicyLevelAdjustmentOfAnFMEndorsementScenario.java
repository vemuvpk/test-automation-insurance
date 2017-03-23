package au.com.cgu.harvest.automation.scenario.farmmotor.endorsements;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateVehicleUnder2TonnesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtPolicyLevelActivity;
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
import au.com.cgu.harvest.pages.PremiumPageLocator;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.VehicleDetailPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Verify Policy Level Adj% for Farm Motor Endorsement Transaction Scenario" )
public class PolicyLevelAdjustmentOfAnFMEndorsementScenario extends
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
        verifyPositiveAdjustmentPercentagePolicyLevel( premiumPage, "5.00%" );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( vehiclePage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        assertPolicyAdjustmentPercentage( premiumPage, "5.00%" );

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
        assertPolicyAdjustmentPercentage( premiumPage, "5.00%" );
        changePolicyLevelAdjustmentAndCheckForReferral( premiumPage, "2.00%", "R056" );
    }

    private void changePolicyLevelAdjustmentAndCheckForReferral( PremiumPage premiumPage,
        String policyLevelAdjPercentage, String expectedRule )
    {
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                policyLevelAdjPercentage ) );

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( PremiumPageLocator.POLICY_ADJUSTMENT_PERCENTAGE_LOCATOR ),
            expectedRule ) );

    }

    private void assertPolicyAdjustmentPercentage( PremiumPage premiumPage,
        String policyLevelAdjPercentage )
    {
        Assert.assertEquals( policyLevelAdjPercentage, premiumPage.getPolicyAdjustmentPercentage() );

    }

    private void verifyPositiveAdjustmentPercentagePolicyLevel( PremiumPage premiumPage,
        String policyLevelAdjPercentage )
    {
        Premium autoRatedFirstVehicle = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehicle = premiumPage.getPremiumForRow( 2 );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                policyLevelAdjPercentage ) );
        Premium autoRatedFirstVehicleAfterPolicyLevelAdj = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehicleAfterPolicyLevelAdj = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumIncreased( autoRatedFirstVehicle,
            autoRatedFirstVehicleAfterPolicyLevelAdj );
        PremiumAssert.assertTotalPremiumIncreased( autoRatedSecondVehicle,
            autoRatedSecondVehicleAfterPolicyLevelAdj );

        BigDecimal policyTotalPremiumAfterAdjustmentPercentage =
            premiumPage.getPolicyTotalPremium();
    }
}
