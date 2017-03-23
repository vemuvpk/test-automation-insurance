package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingTypeToSecondSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddInterestedPartyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddSecondDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddSecondFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ChangeDetailsInSituation1Activity;
import au.com.cgu.harvest.automation.activity.countrypak.ChangeDetailsInSituation2Activity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteFarmPropertyFromThePageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ViewInterestedPartiesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ClosePolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndAcceptActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.ReadOnlyChecker;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Test Multiple Situations add/delete Scenario" )
public class AddMultipleSituationsScenario extends AbstractScenario
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

        SituationDetailPage situationDetailPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                policyDetailPage ) );

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationDetailPage ) );
        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );
        domesticBuildingAndContentsPage =
            performActivity( new AddSecondDwellingType( domesticBuildingAndContentsPage ) );

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationDetailPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        situationDetailPage =
            performActivity( new AddASituationActivity( situationDetailPage ) );

        farmPropertyPage =
            performActivity( new AddSecondFarmPropertyActivity( situationDetailPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        farmPropertyPage =
            performActivity( new DeleteFarmPropertyFromThePageActivity( farmPropertyPage ) );

        domesticBuildingAndContentsPage = addDomesticBuildingToSecondSituation( farmPropertyPage );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingTypeToSecondSituationActivity(
                domesticBuildingAndContentsPage ) );
        domesticBuildingAndContentsPage =
            performActivity( new AddSecondDwellingType( domesticBuildingAndContentsPage ) );

        situationDetailPage =
            performActivity( new ChangeDetailsInSituation1Activity( situationDetailPage ) );

        situationDetailPage =
            performActivity( new ChangeDetailsInSituation2Activity( situationDetailPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( situationDetailPage ) );

        farmPropertyPage =
            performActivity( new AddSecondFarmPropertyActivity( situationDetailPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );
        InterestedPartiesPage interestedPartiesPage =
            performActivity( new ViewInterestedPartiesActivity( roadTransitPage ) );
        interestedPartiesPage =
            performActivity( new AddInterestedPartyActivity( interestedPartiesPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity(
                insuranceHistoryPage ) );

        newBusinessPage =
            performActivity( new SaveAndAcceptActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ClosePolicyActivity( newBusinessPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), policyDetailPage );

        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity(
                policyDetailPage, 1 ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), domesticBuildingAndContentsPage );

    }

    private DomesticBuildingAndContentsPage addDomesticBuildingToSecondSituation(
        FarmPropertyPage farmPropertyPage )
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage;
        domesticBuildingAndContentsPage =
            farmPropertyPage.getNavigationTree().navigateToDomesticBuilding( 2 );

        domesticBuildingAndContentsPage.setAdditionalBusinessContentsSumInsured( "100" );

        CountrypakSection section =
            domesticBuildingAndContentsPage.getNavigationTree().getSection(
                SectionType.DOMESTIC_BUILDING, 2 );
        section.isTaken();
        return domesticBuildingAndContentsPage;
    }
}
