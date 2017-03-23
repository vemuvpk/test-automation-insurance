package au.com.cgu.harvest.automation.scenario.farmmotor;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AddWellKnownFinanceTypeAndInterestedPartyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToInsuranceHistoryPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToPolicyDetailPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PrintPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SelectDocumentsToBeReprintedActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SetAnsweredForAllDriversInInsuranceHistoryPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SetDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStateAfterAcceptanceFromSunriseActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStateFromSunriseActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ClosePolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.ReprintDocumentsPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.farmmotor.IPageWithInterestedParties;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Reprint Amendment Closed Accepted and check for Policy Schedule, Certificate of Currency and New Policy Declaration" )
public class ReprintAmendmentClosedAcceptedScenario extends
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
        // Do and amendment
        NewBusinessPage newBusinessPage;
        policyDetailPage = doAnAmendmentTransaction( vehiclePage );

        // Add a vehicle with Interested party
        AgriculturalVehiclePage agricultreVehiclePage =
            performActivity( new CreateAgriculturalVehicleActivity( policyDetailPage ) );
        IPageWithInterestedParties popup =
            performActivity( new AddWellKnownFinanceTypeAndInterestedPartyActivity(
                agricultreVehiclePage ) );

        // Answer Duty of Disclosure and Insurance History
        insuranceHistoryPage = answerDisclosureAndInsuranceHistoryQuestions( agricultreVehiclePage );

        // Finish policy as covernote and close the policy
        policyDetailPage = finishAndClosePolicy( insuranceHistoryPage );

        // Finish Policy as New Policy with Certificate of Currency
        newBusinessPage = finishPolicyAsNewPolicyWithCertificateOfCurrency( policyDetailPage );

        ReprintDocumentsPage printPage =
            performActivity( new PrintPolicyActivity( newBusinessPage ) );
        ArrayList< String > documentList = new ArrayList< String >();
        documentList.add( "Policy Schedule" );
        documentList.add( "New Policy Declaration" );
        documentList.add( "Certificate of Currency" );
        printPage =
            performActivity( new SelectDocumentsToBeReprintedActivity( printPage, documentList ) );

    }

    private NewBusinessPage finishPolicyAsNewPolicyWithCertificateOfCurrency(
        PolicyDetailPage policyDetailPage )
    {
        NewBusinessPage newBusinessPage;
        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity(
                finishPage, false ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateAfterAcceptanceFromSunriseActivity(
                newBusinessPage, "Amendment Manual Closed Accepted" ) );
        return newBusinessPage;
    }

    private PolicyDetailPage finishAndClosePolicy( InsuranceHistoryPage insuranceHistoryPage )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage;
        newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity( insuranceHistoryPage ) );

        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Amendment Unclosed Complete" ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ClosePolicyActivity( newBusinessPage ) );
        return policyDetailPage;
    }

    private InsuranceHistoryPage answerDisclosureAndInsuranceHistoryQuestions(
        AgriculturalVehiclePage agricultreVehiclePage )
    {
        PolicyDetailPage policyDetailPage;
        InsuranceHistoryPage insuranceHistoryPage;
        policyDetailPage =
            performActivity( new NavigateToPolicyDetailPageActivity( agricultreVehiclePage ) );
        policyDetailPage =
            performActivity( new SetDutyOfDisclosureActivity( policyDetailPage ) );

        insuranceHistoryPage =
            performActivity( new NavigateToInsuranceHistoryPageActivity( policyDetailPage ) );
        insuranceHistoryPage =
            performActivity( new SetAnsweredForAllDriversInInsuranceHistoryPageActivity(
                policyDetailPage ) );
        return insuranceHistoryPage;
    }

    private PolicyDetailPage doAnAmendmentTransaction( PrivateMotorVehiclePage vehiclePage )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity( vehiclePage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        return policyDetailPage;
    }
}
