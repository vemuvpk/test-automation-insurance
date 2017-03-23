package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessInterruptionActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessLiabilityActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddMachineryBreakdownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPersonalIncomeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithFarmContentsAndFarmMachinery;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRulesForManualBusinessInterruptionActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRulesForManualBusinessLiabilityActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRulesForManualDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRulesForManualFarmMachineryPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRulesForManualFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRulesForManualHayFencesActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRulesForManualMachineryBreakdownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRulesForManualPersonalIncomeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRulesForManualPolicyLevelDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRulesForManualPolicyLevelFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRulesForManualRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralForManualBusinessInterruptionActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralForManualBusinessLiabilityActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralForManualDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralForManualFarmMachineryPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralForManualFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralForManualHayFencesActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralForManualMachineryBreakdownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralForManualPersonalIncomeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralForManualPolicyLevelFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralForManualRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralRulesForManualPolicyLevelDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessInterruptionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessLiabilityPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmMachineryPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToMachineryBreakdownPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPersonalIncomePageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPolicyLevelDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPolicyLevelFarmPropertyPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToRoadTransitPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToSituationLevelSectionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToTheftPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelDomesticBuildingPage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelFarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Auto Rated Policy Changes to Manually rated If it cannot be rated in Endorsement Scenario" )
public class AssertAutoRatedPolicyChangesManualInEndorsementScenario extends AbstractScenario
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
            performActivity( new AddDomesticBuildingActivity(
                situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity(
                situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStockActivity( situationLevelSectionPage ) );

        PolicyLevelDomesticBuildingPage policyLevelDomesticBuilding =
            performActivity( new AddPolicyLevelDomesticBuildingActivity( hayFencesLiveStockPage ) );

        PolicyLevelFarmPropertyPage policyLevelFarmPropertypage =
            performActivity( new AddPolicyLevelFarmPropertyActivity( policyLevelDomesticBuilding ) );

        FarmMachineryAndWorkingDogsPage farmmachinery =
            performActivity( new AddFarmMachineryActivity(
                situationLevelSectionPage ) );

        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new AddBusinessInterruptionActivity(
                situationLevelSectionPage ) );

        BusinessLiabilityPage businessLiabilityPage =
            performActivity( new AddBusinessLiabilityActivity(
                situationLevelSectionPage ) );

        MachineryBreakdownPage machineryBreakdownPage =
            performActivity( new AddMachineryBreakdownActivity(
                situationLevelSectionPage ) );

        PersonalIncomePage personalIncomePage =
            performActivity( new AddPersonalIncomeActivity(
                situationLevelSectionPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( personalIncomePage ) );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithFarmContentsAndFarmMachinery(
                roadTransitPage ) );

        doAnEndorsementTransactionActivity( theftPage );

        changePostCodeToRaedown( theftPage );

        domesticBuildingAndContentsPage =
            situationLevelSectionPage.getNavigationTree().navigateToDomesticBuilding( 1 );

        domesticBuildingAndContentsPage =
            performActivity( new AssertErrorRulesForManualDomesticBuildingActivity(
                domesticBuildingAndContentsPage ) );
        domesticBuildingAndContentsPage =
            performActivity( new AssertReferralForManualDomesticBuildingActivity(
                domesticBuildingAndContentsPage ) );

        farmPropertyPage =
            domesticBuildingAndContentsPage.getNavigationTree().navigateToFarmProperty( 1 );

        farmPropertyPage =
            performActivity( new AssertErrorRulesForManualFarmPropertyActivity( farmPropertyPage ) );
        farmPropertyPage =
            performActivity( new AssertReferralForManualFarmPropertyActivity( farmPropertyPage ) );

        hayFencesLiveStockPage =
            farmPropertyPage.getNavigationTree().navigateToHayFenceLiveStock( 1 );
        hayFencesLiveStockPage =
            performActivity( new AssertErrorRulesForManualHayFencesActivity( hayFencesLiveStockPage )
            );
        hayFencesLiveStockPage =
            performActivity( new AssertReferralForManualHayFencesActivity( hayFencesLiveStockPage ) );

        policyLevelDomesticBuilding =
            performActivity( new NavigateToPolicyLevelDomesticBuildingActivity( farmPropertyPage ) );
        policyLevelDomesticBuilding =
            performActivity( new AssertErrorRulesForManualPolicyLevelDomesticBuildingActivity(
                policyLevelDomesticBuilding ) );
        policyLevelDomesticBuilding =
            performActivity( new AssertReferralRulesForManualPolicyLevelDomesticBuildingActivity(
                policyLevelDomesticBuilding ) );

        policyLevelFarmPropertypage =
            performActivity( new NavigateToPolicyLevelFarmPropertyPageActivity(
                policyLevelDomesticBuilding ) );
        policyLevelFarmPropertypage =
            performActivity( new AssertErrorRulesForManualPolicyLevelFarmPropertyActivity(
                policyLevelFarmPropertypage ) );
        policyLevelFarmPropertypage =
            performActivity( new AssertReferralForManualPolicyLevelFarmPropertyActivity(
                policyLevelFarmPropertypage ) );

        farmmachinery =
            performActivity( new NavigateToFarmMachineryPageActivity( policyLevelFarmPropertypage ) );
        farmmachinery =
            performActivity( new AssertErrorRulesForManualFarmMachineryPageActivity( farmmachinery )
            );
        farmmachinery =
            performActivity( new AssertReferralForManualFarmMachineryPageActivity( farmmachinery ) );

        businessInterruptionPage =
            performActivity( new NavigateToBusinessInterruptionPageActivity(
                farmmachinery ) );
        businessInterruptionPage =
            performActivity( new AssertErrorRulesForManualBusinessInterruptionActivity(
                businessInterruptionPage ) );
        businessInterruptionPage =
            performActivity( new AssertReferralForManualBusinessInterruptionActivity(
                businessInterruptionPage ) );

        businessLiabilityPage =
            performActivity( new NavigateToBusinessLiabilityPageActivity(
                businessInterruptionPage ) );
        businessLiabilityPage =
            performActivity( new AssertErrorRulesForManualBusinessLiabilityActivity(
                businessLiabilityPage ) );
        businessLiabilityPage =
            performActivity( new AssertReferralForManualBusinessLiabilityActivity(
                businessLiabilityPage ) );

        machineryBreakdownPage =
            performActivity( new NavigateToMachineryBreakdownPageActivity(
                businessLiabilityPage ) );
        machineryBreakdownPage =
            performActivity( new AssertErrorRulesForManualMachineryBreakdownActivity(
                machineryBreakdownPage ) );
        machineryBreakdownPage =
            performActivity( new AssertReferralForManualMachineryBreakdownActivity(
                machineryBreakdownPage ) );

        personalIncomePage =
            performActivity( new NavigateToPersonalIncomePageActivity(
                machineryBreakdownPage ) );
        personalIncomePage =
            performActivity( new AssertErrorRulesForManualPersonalIncomeActivity(
                personalIncomePage ) );
        personalIncomePage =
            performActivity( new AssertReferralForManualPersonalIncomeActivity(
                personalIncomePage ) );

        roadTransitPage =
            performActivity( new NavigateToRoadTransitPageActivity(
                personalIncomePage ) );
        roadTransitPage =
            performActivity( new AssertErrorRulesForManualRoadTransitActivity(
                roadTransitPage ) );
        roadTransitPage =
            performActivity( new AssertReferralForManualRoadTransitActivity(
                roadTransitPage ) );

        editFarmContentsToCheckForErrorInTheBasePremiumField( policyDetailPage );

    }

    private void editFarmContentsToCheckForErrorInTheBasePremiumField(
        PolicyDetailPage policyDetailPage )
    {
        TheftPage theftPage;
        theftPage =
            performActivity( new NavigateToTheftPageActivity( policyDetailPage ) );
        theftPage.setFarmContentsValue( "20000" );
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( theftPage.FARM_CONTENTS_AT_SITUATION_BASE_PREMIUM ), "E020" ) );
        theftPage.setFarmContentsValue( "10000" );
        wait.until( Rule.isDisplayed(
            By.xpath( theftPage.FARM_CONTENTS_AT_SITUATION_BASE_PREMIUM ), "E020" ) );
    }

    private void doAnEndorsementTransactionActivity( HarvestPage page )
    {
        PolicyDetailPage policyDetailPage;
        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( page ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( finishPage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
    }

    private TheftPage changePostCodeToRaedown( TheftPage theftPage )
    {
        SituationDetailPage situationLevelSectionPage;

        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( theftPage ) );

        situationLevelSectionPage.setSuburbStatePostcode( "Raedown", "NSW", "9999" );

        return theftPage;
    }

}
