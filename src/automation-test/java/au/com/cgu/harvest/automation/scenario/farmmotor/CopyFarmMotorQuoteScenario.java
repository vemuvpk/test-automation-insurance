package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertMandatoryErrorForDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertMandatoryRulesAndCompleteInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CopyPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyForQuoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.VerifyCopyPolicyCommentActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorQuoteActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.NavigateToCommentsPageActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.CommentsPage;
import au.com.cgu.harvest.pages.CopyPolicyPopup;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Copy Farm Motor Quote and check rules, comments and premium values Scenario" )
public class CopyFarmMotorQuoteScenario extends AbstractScenario
{
    @Test
    public void execute()
    {

        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchFarmMotorQuoteActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        InsuranceHistoryPage insuranceHistoryPage =
            performActivity( new InsuranceHistoryActivity( policyDetailPage ) );

        PrivateMotorVehiclePage vehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( vehiclePage ) );
        Premium premium = premiumPage.getPremiumGrid().getFooterRow().getPolicyTotalPremium();

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyForQuoteActivity(
                insuranceHistoryPage ) );
        newBusinessPage.save();

        String quoteNumber = newBusinessPage.getQuoteNumber();

        copyQuoteActivity( welcomePage, newBusinessPage );

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
                "This policy was copied from " + quoteNumber + " on " + new LocalDate() ) );

        premiumPage =
            performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium premiumAfterCopy =
            premiumPage.getPremiumGrid().getFooterRow().getPolicyTotalPremium();

        PremiumAssert.assertTotalPremiumUnchanged( premium, premiumAfterCopy );
    }

    private void copyQuoteActivity( WelcomePage welcomePage, NewBusinessPage newBusinessPage )
    {
        PolicyDetailPage policyDetailPage;
        String quoteNumber = newBusinessPage.getQuoteNumber();
        policyDetailPage =
            performActivity( new LaunchFarmMotorActivity( welcomePage ) );
        CopyPolicyPopup copyPolicyPopup =
            performActivity( new CopyPolicyActivity( policyDetailPage ) );
        copyPolicyPopup.setPolicyNumber( quoteNumber );
        copyPolicyPopup.copy();
    }

}
