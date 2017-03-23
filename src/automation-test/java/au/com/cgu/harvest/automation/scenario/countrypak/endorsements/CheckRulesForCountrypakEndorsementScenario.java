package au.com.cgu.harvest.automation.scenario.countrypak.endorsements;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AssertPolicyStageAndStatusInHeaderActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtPolicyLevelActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverridePremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsWithNewAccountNumberActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.NumberConverter;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumPageLocator;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "CFMC-74" )
@Scenario( "To Check Rules R056,R037, E063, E042 and E077 for Countrypak Endorsement Scenario" )
public class CheckRulesForCountrypakEndorsementScenario extends
    AbstractScenario
{

    @Test
    public void execute()
    {
        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchCountrypakActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsWithNewAccountNumberActivity( policyDetailPage ) );

        CountrypakInsuranceHistoryPage insuranceHistoryPage =
            performActivity( new CountrypakInsuranceHistoryActivity( policyDetailPage ) );

        SituationDetailPage situationLevelSectionPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                insuranceHistoryPage ) );

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );

        FarmMachineryAndWorkingDogsPage farmMachineryPage =
            performActivity( new AddFarmMachineryActivity( domesticBuildingAndContentsPage ) );

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( premiumPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity(
                finishPage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new AssertPolicyStageAndStatusInHeaderActivity( policyDetailPage,
                "Endorsement", "Complete", "Intermediary Statement" ) );
        premiumPage = performActivity( new ViewPremiumActivity( policyDetailPage ) );

        // Now you are in Endorsement transaction premium page
        // Apply Adj% at policy level and check rules
        applyAdjustmentAndOverridesToCheckRules( premiumPage );

    }

    private void applyAdjustmentAndOverridesToCheckRules( PremiumPage premiumPage )
    {
        String policyLevelAdjPercentage = "-15.5";
        String errorPolicyLevelAdjPercentage = "-100";
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                policyLevelAdjPercentage ) );

        // Verify rule R056 and R075
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( PremiumPageLocator.POLICY_ADJUSTMENT_PERCENTAGE_LOCATOR ),
            "R056" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( PremiumPageLocator.POLICY_ADJUSTMENT_PERCENTAGE_LOCATOR ),
            "R075" ) );

        premiumPage.reset();
        // Verify rule E077
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                errorPolicyLevelAdjPercentage ) );
        wait.until( Rule.isDisplayed(
            By.xpath( PremiumPageLocator.POLICY_ADJUSTMENT_PERCENTAGE_LOCATOR ),
            "E077" ) );

        premiumPage.reset();
        // Apply overide premium and check R035
        final String expectedPremiumAdjustmentAmount = "2.00";
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverridePremiumActivity( premiumPage,
                policyTotalPremium.add(
                    NumberConverter.convertCurrencyToBigDecimal( expectedPremiumAdjustmentAmount ) )
                    .toString() ) );
        wait.until( Rule.isDisplayed(
            By.xpath( PremiumPageLocator.PREMIUM_ADJUSTMENT_AMOUNT_LOCATOR ),
            "R035" ) );

        premiumPage.reset();
        // Verify rule E042
        final String errorPremiumAdjustmentAmount = "-10000.00";
        policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage.setTotalOverridePremium( errorPremiumAdjustmentAmount );
        wait.until( Rule.isDisplayed(
            By.xpath( PremiumPageLocator.TOTAL_OVERRIDE_PREMIUM_LOCATOR ),
            "E042" ) );
    }
}
