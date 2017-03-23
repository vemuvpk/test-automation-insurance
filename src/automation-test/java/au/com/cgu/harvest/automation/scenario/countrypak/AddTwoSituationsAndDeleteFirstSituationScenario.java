package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStock2Activity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddSecondDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddSecondFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertSituation2InSituation1Activity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteDomesticBuildingFromNavTreeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteFarmPropertyFromNavTreeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteHayFencesFromNavTreeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFirstSituationAndDeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Add Two Situations with all Sections and delete All sections in First Situation and then Delete First Situation after Suspension Scenario" )
public class AddTwoSituationsAndDeleteFirstSituationScenario extends AbstractScenario
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
        situationLevelSectionPage =
            performActivity( new AddASituationActivity( situationLevelSectionPage ) );

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

        hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStock2Activity( hayFencesLiveStockPage ) );

        farmPropertyPage =
            performActivity( new AddSecondFarmPropertyActivity( hayFencesLiveStockPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddSecondDomesticBuildingActivity( farmPropertyPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( domesticBuildingAndContentsPage ) );

        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new DeleteHayFencesFromNavTreeActivity( policyDetailPage ) );
        policyDetailPage =
            performActivity( new DeleteFarmPropertyFromNavTreeActivity( policyDetailPage, 1 ) );
        policyDetailPage =
            performActivity( new DeleteDomesticBuildingFromNavTreeActivity( policyDetailPage, 1 ) );
        policyDetailPage =
            performActivity( new NavigateToFirstSituationAndDeleteSituationActivity(
                policyDetailPage ) );

        situationLevelSectionPage =
            performActivity( new AssertSituation2InSituation1Activity( policyDetailPage ) );

    }
}
