package au.com.cgu.harvest.automation.scenario.countrypak;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AssertTwoReferralsForTwoScheduledCommentsInReferralPanel;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteCommentActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToCommentsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AddCommentActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.CommentsPage;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.ReferralGridRow;
import au.com.cgu.harvest.pages.ReferralsPageGrid;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Add Schedule Comments to Countrypak Policy  check for Referrals in the referral panel scenario" )
public class CheckReferralsWhenScheduledCommentIsAddedToCountrypakScenario extends AbstractScenario
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

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStockActivity( domesticBuildingAndContentsPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( hayFencesLiveStockPage ) );

        ArrayList< String > sections = new ArrayList< String >();
        sections.add( "Policy" );
        sections.add( "Situation - 1" );
        HarvestPage page =
            performActivity( new AddCommentActivity( roadTransitPage, sections,
                "First Scheduled Comment", "Schedule Comment", "Current Term" ) );

        sections = new ArrayList< String >();
        sections.add( "Situation - 1" );
        sections.add( "Road Transit" );
        page =
            performActivity( new AddCommentActivity( roadTransitPage, sections,
                "Second Scheduled Comment", "Schedule Comment", "Current Term" ) );

        ReferralsPageGrid navPanel =
            performActivity( new AssertTwoReferralsForTwoScheduledCommentsInReferralPanel(
                roadTransitPage ) );
        CommentsPage commentsPage = performActivity( new NavigateToCommentsActivity( page ) );

        createAnEndorsementActivity( commentsPage );

        commentsPage =
            performActivity( new NavigateToCommentsActivity( policyDetailPage ) );
        commentsPage =
            performActivity( new DeleteCommentActivity( commentsPage, 2 ) );

        assertReferralForScheduledCommentInReferralPanel( page );
    }

    private void assertReferralForScheduledCommentInReferralPanel( HarvestPage page )
    {
        ReferralsPageGrid navPanel;
        navPanel =
            page.getReferralAndErrorConditions().clickOnRreferral();

        ReferralGridRow referralGridRow = navPanel.getReferralAtRow( 1 );
        referralGridRow
            .hasValues( "Comments",
                "Referral - R067: A schedule comment has been added, amended or deleted: 'First Sche...'" );
    }

    private void createAnEndorsementActivity( CommentsPage commentsPage )
    {
        PolicyDetailPage policyDetailPage;
        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyAsNewPolicyWithReferralActivity(
                commentsPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
    }
}
