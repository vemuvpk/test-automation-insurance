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
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.PolicyFinderPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "CFMC-857" )
@Scenario( "Assert Transaction Premium Displayed When Viewing NB Transaction After Endorsement transaction For Countrypak Policy Scenario" )
public class AssertTransactionPremiumDisplayedWhenViewingNBTransactionAfterEndorsementScenario
    extends
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

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( situationLevelSectionPage ) );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( roadTransitPage ) );
        Premium sectionPremium = premiumPage.getPremiumForRow( 1 );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( roadTransitPage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        String ourRef = newBusinessPage.getOurRef();

        // View NB Policy and assert Premium Value
        PolicyFinderPage policyFinderPage;
        findAndViewPolicyFromPolicyFinderPage( newBusinessPage, ourRef );

        premiumPage =
            performActivity( new ViewPremiumActivity( roadTransitPage ) );
        Premium nbPemium = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumUnchanged( sectionPremium,
            nbPemium );
        newBusinessPage = premiumPage.getToolbar().close();

        // View NB Policy after Endorsement Transaction and assert Premium Value
        doAnEndorsementTransactionAndViewNBPolicy( newBusinessPage, ourRef );

        premiumPage =
            performActivity( new ViewPremiumActivity( roadTransitPage ) );
        Premium endorsementPemium = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumUnchanged( sectionPremium,
            endorsementPemium );

    }

    private void doAnEndorsementTransactionAndViewNBPolicy( NewBusinessPage newBusinessPage,
        String ourRef )
    {
        PolicyDetailPage policyDetailPage;
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        finishPage.finish();
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        findAndViewPolicyFromPolicyFinderPage( newBusinessPage, ourRef );
    }

    private void findAndViewPolicyFromPolicyFinderPage( NewBusinessPage newBusinessPage,
        String ourRef )
    {
        PolicyFinderPage policyFinderPage = newBusinessPage.findPolicy();
        policyFinderPage.findPolicyByOurRefAndView( ourRef, "H" );
        newBusinessPage = policyFinderPage.viewPolicyFromSearchResults();
        newBusinessPage.viewRisk();
    }
}
