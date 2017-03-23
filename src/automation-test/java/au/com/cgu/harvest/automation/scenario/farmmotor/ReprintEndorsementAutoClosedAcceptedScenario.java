package au.com.cgu.harvest.automation.scenario.farmmotor;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AddWellKnownFinanceTypeAndInterestedPartyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
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
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.ReprintDocumentsPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.farmmotor.IPageWithInterestedParties;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Ignore
@RunWith( ParallelScenarioRunner.class )
@Scenario( "Reprint Documents for a Endorsement AutoClosed Accepted and check for Endorsement Schedule and Certificate of Currency Scenario" )
public class ReprintEndorsementAutoClosedAcceptedScenario extends AbstractScenario
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
            performActivity( new FinishPolicyAsNewPolicyActivity(
                finishPage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        newBusinessPage =
            performActivity( new AssertPolicyStateAfterAcceptanceFromSunriseActivity(
                newBusinessPage, "Endorsement Auto Closed Accepted" ) );
        ReprintDocumentsPage printPage =
            performActivity( new PrintPolicyActivity( newBusinessPage ) );
        ArrayList< String > documentList = new ArrayList< String >();
        documentList.add( "Endorsement Schedule" );
        documentList.add( "Certificate of Currency" );
        printPage =
            performActivity( new SelectDocumentsToBeReprintedActivity( printPage, documentList ) );

    }
}
