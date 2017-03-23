package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessInterruptionActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessLiabilityActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddInterestedPartyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddMachineryBreakdownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPersonalIncomeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithFarmContentsAndFarmMachinery;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessInterruptionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessLiabilityPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToCountrypakInsuranceHistoryPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToCountrypakInsuredDetailsPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmMachineryPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmPropertyPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToHayFencesPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToMachineryBreakdownPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPersonalIncomePageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToRoadTransitPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToSituationLevelSectionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToTheftPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ViewInterestedPartiesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ClosePolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndAcceptActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuredDetailsPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.ReadOnlyChecker;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPage;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert all fields are in Read Only after the policy is closed in Countrypak scenario" )
public class AssertReadOnlyPagesForCountrypakPolicyScenario extends AbstractScenario
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

        TheftPage theftPage =
            performActivity( new AddTheftPageWithFarmContentsAndFarmMachinery(
                situationLevelSectionPage ) );

        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new AddBusinessInterruptionActivity( theftPage ) );

        BusinessLiabilityPage businessLiabilityPage =
            performActivity( new AddBusinessLiabilityActivity( businessInterruptionPage ) );

        MachineryBreakdownPage machineryBreakdownPage =
            performActivity( new AddMachineryBreakdownActivity( businessLiabilityPage ) );

        PersonalIncomePage personalIncomePage =
            performActivity( new AddPersonalIncomeActivity( machineryBreakdownPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( personalIncomePage ) );

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

        insuranceHistoryPage =
            performActivity( new NavigateToCountrypakInsuranceHistoryPageActivity( policyDetailPage )
            );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), insuranceHistoryPage );

        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                insuranceHistoryPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), situationLevelSectionPage );

        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( situationLevelSectionPage,
                1 ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), domesticBuildingAndContentsPage );

        farmPropertyPage =
            performActivity( new NavigateToFarmPropertyPageActivity( situationLevelSectionPage, 1 ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), farmPropertyPage );

        hayFencesLiveStockPage =
            performActivity( new NavigateToHayFencesPageActivity( farmPropertyPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), hayFencesLiveStockPage );

        farmMachineryPage =
            performActivity( new NavigateToFarmMachineryPageActivity(
                domesticBuildingAndContentsPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), farmMachineryPage );

        theftPage =
            performActivity( new NavigateToTheftPageActivity(
                situationLevelSectionPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), theftPage );

        businessInterruptionPage =
            performActivity( new NavigateToBusinessInterruptionPageActivity( theftPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), businessInterruptionPage );

        businessLiabilityPage =
            performActivity( new NavigateToBusinessLiabilityPageActivity( businessInterruptionPage )
            );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), businessLiabilityPage );

        machineryBreakdownPage =
            performActivity( new NavigateToMachineryBreakdownPageActivity( businessLiabilityPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), machineryBreakdownPage );

        personalIncomePage =
            performActivity( new NavigateToPersonalIncomePageActivity( machineryBreakdownPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), personalIncomePage );

        roadTransitPage =
            performActivity( new NavigateToRoadTransitPageActivity( personalIncomePage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), roadTransitPage );

        InsuredDetailsPage insuredDetailsPage =
            performActivity( new NavigateToCountrypakInsuredDetailsPageActivity( roadTransitPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), insuredDetailsPage );

    }
}
