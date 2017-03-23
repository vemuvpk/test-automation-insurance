package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithFarmContentsAndFarmMachinery;
import au.com.cgu.harvest.automation.activity.countrypak.CloseInstalmentPlanPopupActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ViewInstalmentPlanForNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ViewInstalmentPlanInFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStateFromSunriseActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InstalmentPlanPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Create Instalment plan and then add a new Section to Countrypak Policy and chk the Policy Totals have changed Scenario" )
public class CreateInstalmentPlanAndAddNewSectionScenario extends
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

        verifyPolicyTotalChangesWhenNewSectionIsAddedInInstalmentPopup( finishPage );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity(
                insuranceHistoryPage ) );

        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity(
                newBusinessPage, "New Business Auto Closed Complete" ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity(
                newBusinessPage, "New Business Auto Closed Accepted" ) );

    }

    private void verifyPolicyTotalChangesWhenNewSectionIsAddedInInstalmentPopup(
        FinishPage finishPage )
    {
        InstalmentPlanPopup instalmentPlanPopup =
            performActivity( new ViewInstalmentPlanForNewPolicyActivity( finishPage ) );

        String policyTotal = instalmentPlanPopup.getTotalPremiumValue();

        finishPage =
            performActivity( new CloseInstalmentPlanPopupActivity( instalmentPlanPopup ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( finishPage ) );
        finishPage =
            performActivity( new NavigateToFinishPageActivity( roadTransitPage ) );

        instalmentPlanPopup =
            performActivity( new ViewInstalmentPlanInFinishPageActivity( finishPage ) );

        String policyTotalWhenNewSectionAdded =
            new InstalmentPlanPopup( getWebDriver() ).getTotalPremiumValue();

        instalmentPlanPopup.assertNonZeroPremium();

        finishPage =
            performActivity( new CloseInstalmentPlanPopupActivity( instalmentPlanPopup ) );
    }
}
