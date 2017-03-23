package au.com.cgu.harvest.automation.scenario.countrypak.endorsements;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationWithReasonActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakWithOldStartDateActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyRiskDetailsToChangeAttachmentDateActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.DeleteSituationPopup;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "PH-284" )
@Scenario( "Verify Total Loss when complete Situation is deleted for an Endorsement Transaction for Countrypak Scenario" )
public class TotalLossSituationOnEndorsementTransactionForCountrypakScenario extends
    AbstractScenario
{

    @Test
    public void execute()
    {
        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchCountrypakWithOldStartDateActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        CountrypakInsuranceHistoryPage insuranceHistoryPage =
            performActivity( new CountrypakInsuranceHistoryActivity( policyDetailPage ) );

        SituationDetailPage situationLevelSectionPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                insuranceHistoryPage ) );
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );
        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );

        RoadTransitPage roadTransitpage =
            performActivity( new AddRoadTransitActivity( situationLevelSectionPage ) );

        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyAsNewPolicyWithReferralActivity( roadTransitpage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( roadTransitpage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyRiskDetailsToChangeAttachmentDateActivity( newBusinessPage ) );

        situationLevelSectionPage =
            performActivity( new AddASituationActivity( policyDetailPage ) );

        DeleteSituationPopup deleteSituationPopup =
        	performActivity( new DeleteSituationWithReasonActivity( situationLevelSectionPage, 1 ) );

        policyDetailPage = deleteSituationPopup.setReason("Sale of Asset");
    }
}
