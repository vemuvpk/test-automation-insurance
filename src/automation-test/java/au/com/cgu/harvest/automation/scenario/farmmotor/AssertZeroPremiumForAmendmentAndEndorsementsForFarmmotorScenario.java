package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AssertAndSetDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToAgriculturalVehiclePageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFirstVehiclePageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToPolicyDetailPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SetAnsweredForAllDriversInInsuranceHistoryPageActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePageXpath;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePageXpath;
import au.com.cgu.harvest.pages.farmmotor.VehicleDetailPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert a policy with Zero premium value in Amendment and Endorsement Transactions for Farmmotor Scenario" )
public class AssertZeroPremiumForAmendmentAndEndorsementsForFarmmotorScenario extends
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
            performActivity( new CreatePrivateMotorVehicleWithManualPremiumActivity(
                insuranceHistoryPage ) );

        // Check Negative premium is allowed in amendment transaction
        policyDetailPage = doAnAmendmentTransaction( insuranceHistoryPage, vehiclePage );
        policyDetailPage = assertNegativePremiumRuleForNewRisk( policyDetailPage );
        assertNegativePremiumInAmendmentTransaction( policyDetailPage, "100", "00.0" );

        AgriculturalVehiclePage agriculturalVehiclePage;
        agriculturalVehiclePage =
            performActivity( new NavigateToAgriculturalVehiclePageActivity( policyDetailPage ) );
        VehicleSummaryPage vehicleSummaryPage =
            performActivity( new DeleteVehicleActivity< VehicleDetailPage >(
                agriculturalVehiclePage ) );

        // Check Negative premium is allowed in Endorsement transaction
        doAnEndorsementTransaction( policyDetailPage );
        assertNegativePremiumInEndorsementTransaction( policyDetailPage, "50", "0" );

    }

    private PolicyDetailPage doAnAmendmentTransaction(
        InsuranceHistoryPage insuranceHistoryPage, PrivateMotorVehiclePage vehiclePage )
    {
        PolicyDetailPage policyDetailPage;
        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( vehiclePage ) );

        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyAsCoverNoteWithReferralActivity(
                insuranceHistoryPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        return policyDetailPage;
    }

    private void doAnEndorsementTransaction( PolicyDetailPage policyDetailPage )
    {
        FinishPage finishPage;
        OutstandingReferralsPopup referralPopup;
        NewBusinessPage newBusinessPage;
        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        referralPopup =
            performActivity( new FinishPolicyAsNewPolicyWithReferralActivity( finishPage ) );
        newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
    }

    private void assertNegativePremiumInAmendmentTransaction( PolicyDetailPage policyDetailPage,
        String value, String premium )
    {
        String _value = value;
        String _premium = premium;

        PrivateMotorVehiclePage vehiclePage;
        vehiclePage =
            performActivity( new NavigateToFirstVehiclePageActivity( policyDetailPage ) );
        vehiclePage.setVehicleValue( _value );

        vehiclePage.setBasePremium( _premium );
        vehiclePage.setWindScreenPremium( "15" );
        // assert R036
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( PrivateMotorVehiclePageXpath.BASE_PREMIUM ), "R036" ) );
    }

    private void assertNegativePremiumInEndorsementTransaction( PolicyDetailPage policyDetailPage,
        String value, String premium )
    {
        String _value = value;
        String _premium = premium;

        PrivateMotorVehiclePage vehiclePage;
        vehiclePage =
            performActivity( new NavigateToFirstVehiclePageActivity( policyDetailPage ) );
        vehiclePage.setVehicleValue( _value );

        vehiclePage.setTransactionBasePremium( _premium );
        vehiclePage.setTransactionWindscreenPremium( "15" );
        // assert R036
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( PrivateMotorVehiclePageXpath.TRANSACTION_BASE_PREMIUM ), "R036" ) );
    }

    private PolicyDetailPage assertNegativePremiumRuleForNewRisk( PolicyDetailPage
        policyDetailPage )
    {
        AgriculturalVehiclePage agriculturalVehiclePage =
            performActivity( new CreateAgriculturalVehicleWithManualPremiumActivity(
                policyDetailPage ) );
        agriculturalVehiclePage.setVehicleValue( "10000" );
        agriculturalVehiclePage.setBasePremium( "-150.00" );
        // chk for E010
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( AgriculturalVehiclePageXpath.BASE_PREMIUM ), "E010" ) );
        policyDetailPage =
            performActivity( new NavigateToPolicyDetailPageActivity( agriculturalVehiclePage ) );
        policyDetailPage =
            performActivity( new AssertAndSetDutyOfDisclosureActivity( policyDetailPage ) );
        InsuranceHistoryPage insuranceHistoryPage =
            performActivity( new SetAnsweredForAllDriversInInsuranceHistoryPageActivity(
                policyDetailPage ) );
        agriculturalVehiclePage =
            performActivity( new NavigateToAgriculturalVehiclePageActivity( policyDetailPage ) );
        agriculturalVehiclePage.setBasePremium( "150.00" );
        return policyDetailPage;
    }
}
