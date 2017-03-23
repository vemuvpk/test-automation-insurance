package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AddCancellationReasonAndFinishThePolicyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithMonthlyDirectDebitActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStateFromSunriseActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.TerminatePolicyActivity;
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
@Scenario( "Intermediary Initiated Cancellation of Instalment Policy Finished as New Policy for Countrypak Scenario" )
public class IntermediaryInitiatedCancellationOfInstalmentPolicyScenario extends
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
            performActivity( new FinishPolicyAsNewPolicyWithMonthlyDirectDebitActivity( finishPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "New Business Auto Closed Complete" ) );
        newBusinessPage = performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "New Business Auto Closed Accepted" ) );

        PremiumPage premiumPage =
            performActivity( new TerminatePolicyActivity( newBusinessPage ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( premiumPage ) );

        finishPage = performActivity( new NavigateToFinishPageActivity( premiumPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), finishPage );

        premiumPage =
            performActivity( new ViewPremiumActivity( finishPage ) );
        newBusinessPage =
            performActivity( new AddCancellationReasonAndFinishThePolicyActivity( premiumPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Cancellation Auto Closed Complete" ) );

        newBusinessPage = performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Cancellation Auto Closed Accepted" ) );

    }
}
