package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertMandatoryErrorForDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertMandatoryRulesAndCompleteInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertValuesInReferralGridForManualFMActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CopyPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFirstVehiclePageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.VerifyCopyPolicyCommentActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.NavigateToCommentsPageActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.CommentsPage;
import au.com.cgu.harvest.pages.CopyPolicyPopup;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.ReferralsPageGrid;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePageXpath;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Copy Farm Motor Policy with a referral and check rules, comments and premium values Scenario" )
public class CopyFarmMotorPolicyWithReferralScenario extends AbstractScenario
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

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( vehiclePage ) );
        Premium premium = premiumPage.getPremiumGrid().getFooterRow().getPolicyTotalPremium();

        ReferralsPageGrid navPanel =
            performActivity( new AssertValuesInReferralGridForManualFMActivity(
                premiumPage ) );

        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyAsNewPolicyWithReferralActivity(
                insuranceHistoryPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        String policyNumber = newBusinessPage.getPolicyNumber();

        copyPolicyActivity( welcomePage, newBusinessPage );

        policyDetailPage =
            performActivity( new AssertMandatoryErrorForDutyOfDisclosureActivity( policyDetailPage ) );
        policyDetailPage.setDutyOfDisclosure( "true" );
        insuranceHistoryPage =
            performActivity( new AssertMandatoryRulesAndCompleteInsuranceHistoryActivity(
                policyDetailPage ) );

        CommentsPage commentsPage =
            performActivity( new NavigateToCommentsPageActivity( insuranceHistoryPage ) );
        commentsPage =
            performActivity( new VerifyCopyPolicyCommentActivity( commentsPage, 1,
                "This policy was copied from " + policyNumber + " on " + new LocalDate() ) );

        vehiclePage =
            performActivity( new NavigateToFirstVehiclePageActivity( commentsPage ) );

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( PrivateMotorVehiclePageXpath.BASE_PREMIUM ),
            "E020" ) );
    }

    private void copyPolicyActivity( WelcomePage welcomePage, NewBusinessPage newBusinessPage )
    {
        PolicyDetailPage policyDetailPage;
        String policyNumber = newBusinessPage.getPolicyNumber();
        policyDetailPage =
            performActivity( new LaunchFarmMotorActivity( welcomePage ) );
        CopyPolicyPopup copyPolicyPopup =
            performActivity( new CopyPolicyActivity( policyDetailPage ) );
        copyPolicyPopup.setPolicyNumber( policyNumber );
        copyPolicyPopup.copy();
    }

}
