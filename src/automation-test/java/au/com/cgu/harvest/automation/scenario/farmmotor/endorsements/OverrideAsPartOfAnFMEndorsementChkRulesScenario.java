package au.com.cgu.harvest.automation.scenario.farmmotor.endorsements;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualTransactionPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverridePremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.NumberConverter;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumPageLocator;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Verify Override is reset when hit Reset and Check for rules in Endorsement Transaction Scenario" )
public class OverrideAsPartOfAnFMEndorsementChkRulesScenario extends
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

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( vehiclePage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( policyDetailPage ) );

        applyPremiumOverrideToThePolicy( premiumPage );

        verifyOverrideIsReset( premiumPage );

        applyPremiumOverrideToCheckReferral( premiumPage );

        verifyErrorMessageWhenRefundAmountExceeds( premiumPage );

        verifyErrorMessageForNonApplicableOverride( premiumPage );
    }

    private void verifyErrorMessageForNonApplicableOverride( PremiumPage premiumPage )
    {
        premiumPage.reset();
        PrivateMotorVehiclePage vehiclePage;
        vehiclePage =
            performActivity( new CreatePrivateMotorVehicleWithManualTransactionPremiumActivity(
                premiumPage ) );
        premiumPage = performActivity( new ViewPremiumActivity( premiumPage ) );
    }

    private void verifyErrorMessageWhenRefundAmountExceeds( PremiumPage premiumPage )
    {
        PrivateMotorVehiclePage vehiclePage;
        premiumPage.reset();
        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 1 ) );
        vehiclePage.deleteVehicle();

        premiumPage = performActivity( new ViewPremiumActivity( premiumPage ) );

    }

    private void verifyOverrideIsReset( PremiumPage premiumPage )
    {
        premiumPage.reset();
        premiumPage = performActivity( new ViewPremiumActivity( premiumPage ) );
        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );
    }

    public void applyPremiumOverrideToThePolicy( PremiumPage premiumPage )
    {
        final String expectedPremiumAdjustmentAmount = "$0.75";

        // Apply a premium override of "$0.75"
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverridePremiumActivity( premiumPage,
                policyTotalPremium.add(
                    NumberConverter.convertCurrencyToBigDecimal( expectedPremiumAdjustmentAmount ) )
                    .toString() ) );
    }

    private void applyPremiumOverrideToCheckReferral( PremiumPage premiumPage )
    {
        final String expectedPremiumAdjustmentAmount = "$1.75";

        // Apply a premium override of "$0.75"
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverridePremiumActivity( premiumPage,
                policyTotalPremium.add(
                    NumberConverter.convertCurrencyToBigDecimal( expectedPremiumAdjustmentAmount ) )
                    .toString() ) );

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( PremiumPageLocator.PREMIUM_ADJUSTMENT_AMOUNT_LOCATOR ), "R035" ) );

    }
}
