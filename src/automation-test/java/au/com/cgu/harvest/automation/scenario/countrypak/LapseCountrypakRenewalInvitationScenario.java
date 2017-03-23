package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.FinishPolicyAsRenewalInvitationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStateFromSunriseActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ClosePolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.RenewAndRenewRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.TerminatePolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ViewRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.ReadOnlyChecker;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Lapse a Renewal Invitation for Countrypak Scenario" )
public class LapseCountrypakRenewalInvitationScenario extends
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

        RoadTransitPage roadTransit =
            performActivity( new AddRoadTransitActivity( situationLevelSectionPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( roadTransit ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity( finishPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "New Business Unclosed Complete" ) );
        newBusinessPage = performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "New Business Unclosed Accepted" ) );

        policyDetailPage =
            performActivity( new ClosePolicyActivity( newBusinessPage ) );
        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        finishPage.finish();
        newBusinessPage = performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new RenewAndRenewRiskDetailsActivity( newBusinessPage ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

        newBusinessPage =
            performActivity( new FinishPolicyAsRenewalInvitationActivity( finishPage ) );

        // OutstandingReferralsPopup referralPopup =
        // performActivity( new FinishPolicyAsClosedRenewalWithReferralActivity( policyDetailPage )
        // );
        // newBusinessPage =
        // performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );

        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Renewal Unclosed Complete" ) );

        newBusinessPage = performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Renewal Unclosed Accepted" ) );

        PremiumPage premiumPage =
            performActivity( new TerminatePolicyActivity( newBusinessPage ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( premiumPage ) );
        finishPage.finish();

        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Renewal Lapsed Complete" ) );

        newBusinessPage = performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Renewal Lapsed Accepted" ) );

        policyDetailPage = performActivity( new ViewRiskDetailsActivity( newBusinessPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), policyDetailPage );

        premiumPage =
            performActivity( new ViewPremiumActivity( policyDetailPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), premiumPage );

        finishPage = performActivity( new NavigateToFinishPageActivity( premiumPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), finishPage );

    }
}
