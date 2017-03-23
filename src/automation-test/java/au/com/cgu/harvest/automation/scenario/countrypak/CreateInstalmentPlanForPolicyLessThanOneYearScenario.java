package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.joda.time.LocalDate;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithFarmContentsAndFarmMachinery;
import au.com.cgu.harvest.automation.activity.countrypak.CloseInstalmentPlanPopupActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ViewInstalmentPlanForNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ViewInstalmentPlanInFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStateFromSunriseActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InstalmentPlanPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Create Instalment Plan for Countrypak with Policy Term Less than One Year Scenario" )
public class CreateInstalmentPlanForPolicyLessThanOneYearScenario extends
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

        DomesticBuildingAndContentsPage domesticBuildingPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );
        domesticBuildingPage =
            performActivity( new AddDwellingType( domesticBuildingPage ) );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithFarmContentsAndFarmMachinery( domesticBuildingPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( theftPage ) );

        InstalmentPlanPopup instalmentPlanPopup =
            performActivity( new ViewInstalmentPlanForNewPolicyActivity( finishPage ) );

        finishPage =
            performActivity( new CloseInstalmentPlanPopupActivity( instalmentPlanPopup ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( finishPage ) );
        newBusinessPage =
            performActivity( new SaveAndEditRiskDetailsActivity( newBusinessPage ) );

        newBusinessPage.setEndDate( new LocalDate().plusMonths( 7 ).toString( "dd/MM/yyyy" ) );

        policyDetailPage = newBusinessPage.modifyRiskDetails();

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

        instalmentPlanPopup =
            performActivity( new ViewInstalmentPlanInFinishPageActivity( finishPage ) );

        finishPage =
            performActivity( new CloseInstalmentPlanPopupActivity( instalmentPlanPopup ) );

        newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( finishPage ) );

        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity(
                newBusinessPage, "New Business Auto Closed Complete" ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity(
                newBusinessPage, "New Business Auto Closed Accepted" ) );

    }
}
