package au.com.cgu.harvest.automation.scenario.countrypak;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToCommentsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.VerifyCommentActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AddCommentActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertCountOfCommentsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.CommentsPage;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.ReferralAndErrorConditions;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Add Comments to Countrypak Policy at both Situation and Section level scenario" )
public class AddCommentsAtSituationAndSectionLevelToCountrypakPolicyScenario extends
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

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStockActivity( farmPropertyPage ) );

        FarmMachineryAndWorkingDogsPage farmMachineryPage =
            performActivity( new AddFarmMachineryActivity( domesticBuildingAndContentsPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( farmMachineryPage ) );

        ArrayList< String > sections = new ArrayList< String >();
        sections.add( "Policy" );
        HarvestPage page =
            performActivity( new AddCommentActivity( farmMachineryPage, sections,
                "Policy Level Comment", "Internal Comment", "Current Term" ) );

        sections = new ArrayList< String >();
        sections.add( "Situation - 1" );
        page =
            performActivity( new AddCommentActivity( page, sections,
                "Section Level Comment", "Internal Comment", "Current Term" ) );

        ReferralAndErrorConditions navPanel =
            performActivity( new AssertCountOfCommentsActivity( page, "2" ) );
        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( roadTransitPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        CommentsPage commentsPage =
            performActivity( new NavigateToCommentsActivity( policyDetailPage ) );

        commentsPage =
            performActivity( new VerifyCommentActivity( commentsPage, 1, "Section Level Comment",
                "Internal Comment", "Current Term", "Situation - 1\nRoad Transit" ) );

        commentsPage =
            performActivity( new VerifyCommentActivity( commentsPage, 2, "Policy Level Comment",
                "Internal Comment", "Current Term", "Policy\nRoad Transit" ) );

    }
}
