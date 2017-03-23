package au.com.cgu.harvest.automation.scenario.countrypak;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertMandatoryRulesAndCompleteInsuranceHistoryForCPActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertMandatoryErrorForDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CopyPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.VerifyCopyPolicyCommentActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.NavigateToCommentsPageActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.CommentsPage;
import au.com.cgu.harvest.pages.CopyPolicyPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelDomesticBuildingPage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelFarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Copy Countrypak Policy and check rules, comments and premium values Scenario" )
public class CopyCountrypakPolicyScenario extends AbstractScenario
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

        PolicyLevelDomesticBuildingPage domesticPage =
            performActivity( new AddPolicyLevelDomesticBuildingActivity( situationLevelSectionPage ) );

        PolicyLevelFarmPropertyPage farmPropertyPage =
            performActivity( new AddPolicyLevelFarmPropertyActivity( domesticPage ) );

        RoadTransitPage roadTransit =
            performActivity( new AddRoadTransitActivity( situationLevelSectionPage ) );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( roadTransit ) );
        Premium premium = premiumPage.getPremiumGrid().getFooterRow().getPolicyTotalPremium();

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity(
                insuranceHistoryPage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        String policyNumber = newBusinessPage.getPolicyNumber();

        copyPolicyActivity( welcomePage, newBusinessPage );

        policyDetailPage =
            performActivity( new AssertMandatoryErrorForDutyOfDisclosureActivity( policyDetailPage ) );
        policyDetailPage.setDutyOfDisclosure( "true" );
        insuranceHistoryPage =
            performActivity( new AssertMandatoryRulesAndCompleteInsuranceHistoryForCPActivity(
                policyDetailPage ) );

        CommentsPage commentsPage =
            performActivity( new NavigateToCommentsPageActivity( insuranceHistoryPage ) );
        commentsPage =
            performActivity( new VerifyCopyPolicyCommentActivity( commentsPage, 1,
                "This policy was copied from " + policyNumber + " on " + new LocalDate() ) );

        premiumPage =
            performActivity( new ViewPremiumActivity( commentsPage ) );

        Premium premiumAfterCopy =
            premiumPage.getPremiumGrid().getFooterRow().getPolicyTotalPremium();

        PremiumAssert.assertTotalPremiumUnchanged( premium, premiumAfterCopy );
    }

    private void copyPolicyActivity( WelcomePage welcomePage, NewBusinessPage newBusinessPage )
    {
        PolicyDetailPage policyDetailPage;
        String policyNumber = newBusinessPage.getPolicyNumber();
        policyDetailPage =
            performActivity( new LaunchCountrypakActivity( welcomePage ) );
        CopyPolicyPopup copyPolicyPopup =
            performActivity( new CopyPolicyActivity( policyDetailPage ) );
        copyPolicyPopup.setPolicyNumber( policyNumber );
        copyPolicyPopup.copy();
    }

}
