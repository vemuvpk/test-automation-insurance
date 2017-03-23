package au.com.cgu.harvest.automation.scenario.farmmotor;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddWellKnownFinanceTypeAndInterestedPartyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithCOCAndReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PrintPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SelectDocumentsToBeReprintedActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStateAfterAcceptanceFromSunriseActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.RenewAndRenewRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.ReprintDocumentsPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.farmmotor.IPageWithInterestedParties;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Ignore
@RunWith( ParallelScenarioRunner.class )
@Scenario( "Reprint Documents for a Renewal AutoClosed Accepted and check for Renewal Schedule and Certificate of Currency Scenario" )
public class ReprintRenewalClosedAcceptedScenario extends AbstractScenario
{
    // TESTING-301 Story not yet developed so test Ignored
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
            performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );
        IPageWithInterestedParties popup =
            performActivity( new AddWellKnownFinanceTypeAndInterestedPartyActivity( vehiclePage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity(
                vehiclePage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity(
                finishPage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new RenewAndRenewRiskDetailsActivity( newBusinessPage ) );
        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyAsNewPolicyWithCOCAndReferralActivity(
                finishPage ) );
        newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateAfterAcceptanceFromSunriseActivity(
                newBusinessPage, "Renewal Auto Closed Accepted" ) );

        ReprintDocumentsPage printPage =
            performActivity( new PrintPolicyActivity( newBusinessPage ) );
        ArrayList< String > documentList = new ArrayList< String >();
        // documentList.add( "Renewal Schedule" );
        documentList.add( "Policy Schedule" );
        documentList.add( "Certificate of Currency" );
        printPage =
            performActivity( new SelectDocumentsToBeReprintedActivity( printPage, documentList ) );

    }
}
