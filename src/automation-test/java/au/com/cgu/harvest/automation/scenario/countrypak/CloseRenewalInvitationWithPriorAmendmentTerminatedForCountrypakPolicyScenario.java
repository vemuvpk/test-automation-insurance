package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ClosePolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.RenewAndRenewRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.PolicyFinderPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "CFMC-823" )
@Scenario( "Close Renewal Invitation With Prior Amendment Terminated For Countrypak Policy Scenario" )
public class CloseRenewalInvitationWithPriorAmendmentTerminatedForCountrypakPolicyScenario extends
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
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        CountrypakInsuranceHistoryPage insuranceHistoryPage =
            performActivity( new CountrypakInsuranceHistoryActivity( policyDetailPage ) );

        SituationDetailPage situationLevelSectionPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                insuranceHistoryPage ) );
        // Create a Renewal Invitation for CPK Policy
        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( situationLevelSectionPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( roadTransitPage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new RenewAndRenewRiskDetailsActivity( newBusinessPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        finishPage.setSaveTransactionAs( "Invitation" );
        finishPage.finish();

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        String policyNumber = newBusinessPage.getPolicyNumber();
        String attachmentDate = newBusinessPage.getAttachmentDate();

        // Amend renewal Invitation, Suspend, save and edit the policy after Amend
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        newBusinessPage =
            performActivity( new SuspendActivity( policyDetailPage ) );
        newBusinessPage.save();

        // Delete the policy after Suspend and saved in BMS
        newBusinessPage.delete();

        // Search for the renewal invitation, and click close
        PolicyFinderPage policyFinderPage = newBusinessPage.findPolicy();
        policyFinderPage.findPolicyByNumberAndDate( policyNumber, attachmentDate );
        policyFinderPage.viewPolicyFromSearchResults();

        policyDetailPage =
            performActivity( new ClosePolicyActivity( newBusinessPage ) );

        // In Harvest, finish the renewal invitation and check no errors present in Sunrise
        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        finishPage.finish();

        newBusinessPage.getAcceptance();
        newBusinessPage.assertNoErrorPresent();

    }
}
