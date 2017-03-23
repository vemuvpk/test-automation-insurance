package au.com.cgu.harvest.automation.scenario.countrypak.renewals;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.FinishPolicyAsRenewalInvitationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertPolicyDateInHeaderActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStateFromSunriseActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ClosePolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.RenewPolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Change Instalment Status of a Policy at Renewal Transaction For Countrypak Policy And Finish as Renewal Invitation Scenario" )
public class ChangeInstalmentStatusOfPolicyAtRenewalTransactionAndFinishAsRenewalInvitationScenario
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

        RoadTransitPage roadTransit =
            performActivity( new AddRoadTransitActivity( situationLevelSectionPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity( roadTransit ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        String policyNumber = newBusinessPage.getPolicyNumber();
        AbstractScenario.getScenarioLogger().trace( "Policy NUmber is : " + policyNumber );

        policyDetailPage =
            performActivity( new ClosePolicyActivity( newBusinessPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        policyDetailPage.getCountrypakPolicyNumber();
        finishPage.finish();

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new RenewPolicyActivity( newBusinessPage,
                new LocalDate().plusYears( 1 ).toString( "dd/MM/yyyy" ),
                new LocalDate().plusYears( 2 ).toString( "dd/MM/yyyy" ) ) );

        policyDetailPage =
            performActivity( new AssertPolicyDateInHeaderActivity( policyDetailPage,
                new LocalDate().plusYears( 1 ).toString( "dd/MM/yyyy" ),
                new LocalDate().plusYears( 2 ).toString( "dd/MM/yyyy" ),
                new LocalDate().plusYears( 1 ).toString( "dd/MM/yyyy" ) ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

        newBusinessPage =
            performActivity( new FinishPolicyAsRenewalInvitationActivity( finishPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Renewal Unclosed Complete" ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Renewal Unclosed Accepted" ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        finishPage.setSaveTransactionAs( "Invitation" );

        newBusinessPage = performActivity( new SuspendActivity( finishPage ) );

        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Amendment Unclosed Incomplete" ) );

        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        policyDetailPage.setDutyOfDisclosure( "true" );
        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        finishPage.finish();

        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Amendment Unclosed Complete" ) );

    }
}
