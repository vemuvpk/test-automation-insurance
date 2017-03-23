package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingTypeWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessInterruptionWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessLiabilityWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingTypeWithManualPremium;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddMachineryBreakdownWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPersonalIncomeWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelDomesticBuildingWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelFarmPropertyWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForRaedownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessInterruptionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessLiabilityPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmMachineryPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmPropertyPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToHayFencesPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToMachineryBreakdownPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPersonalIncomePageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPolicyLevelDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPolicyLevelFarmPropertyPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToRoadTransitPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToTheftPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPageXpath;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPageXpath;
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
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "CFMC-494" )
@Scenario( "Assert Manual Premium is Reset for change in rating Factor for a Countrypak Policy Scenario" )
public class AssertManualPremiumResetForRatingFactorChangeScenario extends AbstractScenario
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
            performActivity( new CreateASituationForRaedownActivity(
                insuranceHistoryPage ) );

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingWithManualPremiumActivity(
                situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingTypeWithManualPremium( domesticBuildingAndContentsPage ) );

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyWithManualPremiumActivity(
                domesticBuildingAndContentsPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingTypeWithManualPremiumActivity( farmPropertyPage ) );

        PolicyLevelDomesticBuildingPage policyLevelDomesticBuilding =
            performActivity( new AddPolicyLevelDomesticBuildingWithManualPremiumActivity(
                farmPropertyPage ) );

        PolicyLevelFarmPropertyPage policyLevelFarmPropertypage =
            performActivity( new AddPolicyLevelFarmPropertyWithManualPremiumActivity(
                policyLevelDomesticBuilding ) );

        FarmMachineryAndWorkingDogsPage farmMachineryPage =
            performActivity( new AddFarmMachineryWithManualPremiumActivity(
                policyLevelFarmPropertypage ) );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithManualPremiumActivity(
                farmMachineryPage ) );

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStockWithManualPremiumActivity( theftPage )
            );

        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new AddBusinessInterruptionWithManualPremiumActivity(
                hayFencesLiveStockPage ) );

        BusinessLiabilityPage businessLiabilityPage =
            performActivity( new AddBusinessLiabilityWithManualPremiumActivity(
                businessInterruptionPage ) );

        MachineryBreakdownPage machineryBreakdownPage =
            performActivity( new AddMachineryBreakdownWithManualPremiumActivity(
                businessLiabilityPage ) );

        PersonalIncomePage personalIncomePage =
            performActivity( new AddPersonalIncomeWithManualPremiumActivity(
                machineryBreakdownPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitWithManualPremiumActivity( personalIncomePage ) );

        suspendSaveAndEditThePolicy( roadTransitPage );

        domesticBuildingAndContentsPage =
            assertPremiumResetForDomesticBuilding( policyDetailPage );

        assertPremiumResetForFarmProperty( domesticBuildingAndContentsPage );

        assertPremiumResetForHayFences( farmPropertyPage );

        assertPremiumResetForPolicyLevelDomesticBuilding( hayFencesLiveStockPage );

        assertPremiumResteForPolicyLevelFarmProperty( policyLevelDomesticBuilding );

        assertPremiumResetForFarmMachinery( hayFencesLiveStockPage );

        assertPremiumResetForTheft( farmMachineryPage );

        assertPremiumResetForBusinessInterruption( theftPage );

        assertPremiumResetForBusinessLiability( policyDetailPage );

        assertPremiumResetForMachineryBreakdown( businessLiabilityPage );

        assertPremiumResetForPersonalIncome( machineryBreakdownPage );

        assertPremiumResetForRoadTransit( personalIncomePage );
    }

    private void assertPremiumResteForPolicyLevelFarmProperty(
        PolicyLevelDomesticBuildingPage policyLevelDomesticBuilding )
    {
        PolicyLevelFarmPropertyPage policyLevelfarmPropertyPage;
        policyLevelfarmPropertyPage =
            performActivity( new NavigateToPolicyLevelFarmPropertyPageActivity(
                policyLevelDomesticBuilding ) );
        policyLevelfarmPropertyPage.getSpecifiedItemsGrid().addRow( "Description - 3", "5000" );
        policyLevelfarmPropertyPage.ruleTriggered(
            policyLevelfarmPropertyPage.SPECIFIED_ITEMS_BASE_PREMIUM_LOCATOR,
            "E020" );
        policyLevelfarmPropertyPage.setSpecifiedItemsBasePremium( "55" );

    }

    private void assertPremiumResetForPolicyLevelDomesticBuilding(
        HayFencesLiveStockPage hayFencesLiveStockPage )
    {
        PolicyLevelDomesticBuildingPage policyLevelDomesticBuilding;
        policyLevelDomesticBuilding =
            performActivity( new NavigateToPolicyLevelDomesticBuildingActivity(
                hayFencesLiveStockPage ) );
        policyLevelDomesticBuilding.setUnspecifiedValuableSumInsured( "500" );
        policyLevelDomesticBuilding.ruleTriggered(
            policyLevelDomesticBuilding.UNSPECIFIED_VALUABLES_BASE_PREMIUM_LOCATOR,
            "E020" );
        policyLevelDomesticBuilding.setUnspecifiedValuableBasePremium( "55" );

        policyLevelDomesticBuilding.getSpecifiedValuablesGrid().addRow( "Description - 3", "5000" );
        policyLevelDomesticBuilding.ruleTriggered(
            policyLevelDomesticBuilding.SPECIFIED_VALUABLES_BASE_PREMIUM_LOCATOR,
            "E020" );
        policyLevelDomesticBuilding.setSpecifiedValuableBasePremium( "55" );

    }

    private void assertPremiumResetForRoadTransit( PersonalIncomePage personalIncomePage )
    {
        RoadTransitPage roadTransitPage;
        roadTransitPage =
            performActivity( new NavigateToRoadTransitPageActivity( personalIncomePage ) );
        roadTransitPage.setSumInsured( "5000" );
        roadTransitPage.ruleTriggered( roadTransitPage.BASE_PREMIUM, "E020" );
        roadTransitPage.setBasePremium( "55" );
    }

    private void assertPremiumResetForPersonalIncome( MachineryBreakdownPage machineryBreakdownPage )
    {
        PersonalIncomePage personalIncomePage;
        personalIncomePage =
            performActivity( new NavigateToPersonalIncomePageActivity( machineryBreakdownPage ) );
        personalIncomePage.setDateOfBirth( "1/01/84" );
        personalIncomePage.ruleTriggered( personalIncomePage.ACCIDENT_BASE_PREMIUM_LOCATOR, "E020" );
        personalIncomePage.ruleTriggered( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR, "E020" );
        personalIncomePage.setAccidentBasePremium( "55" );
        personalIncomePage.setIllnessBasePremium( "55" );

        personalIncomePage.setGender( "Female" );
        personalIncomePage.ruleTriggered( personalIncomePage.ACCIDENT_BASE_PREMIUM_LOCATOR, "E020" );
        personalIncomePage.ruleTriggered( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR, "E020" );
        personalIncomePage.setAccidentBasePremium( "55" );
        personalIncomePage.setIllnessBasePremium( "55" );

        personalIncomePage.setNumberOfAccidentUnits( "4" );
        personalIncomePage.ruleTriggered( personalIncomePage.ACCIDENT_BASE_PREMIUM_LOCATOR, "E020" );
        personalIncomePage.setAccidentBasePremium( "55" );

        personalIncomePage.setNumberOfIllnessUnits( "2" );
        personalIncomePage.ruleTriggered( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR, "E020" );
        personalIncomePage.setIllnessBasePremium( "55" );

        personalIncomePage.setDoYouWishToReducePremium( "true" );
        personalIncomePage.setNominateNumberOfWeeks( "2" );
        personalIncomePage.ruleTriggered( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR, "E020" );
        personalIncomePage.setIllnessBasePremium( "55" );

        personalIncomePage.setAreYouNonSmoker( "false" );
        personalIncomePage.ruleTriggered( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR, "E020" );
        personalIncomePage.setIllnessBasePremium( "55" );
    }

    private void assertPremiumResetForMachineryBreakdown(
        BusinessLiabilityPage businessLiabilityPage )
    {
        MachineryBreakdownPage machineryBreakdownPage;
        machineryBreakdownPage =
            performActivity( new NavigateToMachineryBreakdownPageActivity( businessLiabilityPage ) );
        machineryBreakdownPage.setBlanketCoverRatingUnits( "4" );
        machineryBreakdownPage.ruleTriggered( machineryBreakdownPage.BLANKET_COVER_BASE_PREMIUM,
            "E020" );
        machineryBreakdownPage.setBlanketCoverBasePremium( "55" );

        machineryBreakdownPage.setSpecifiedItemsRatingUnits( "4" );
        machineryBreakdownPage.ruleTriggered( machineryBreakdownPage.SPECIFIED_ITEMS_BASE_PREMIUM,
            "E020" );
        machineryBreakdownPage.setSpecifiedItemsBasePremium( "55" );

        machineryBreakdownPage.getSpecifiedItemsGrid().deleteNameValueRow( 1 );
        machineryBreakdownPage.ruleTriggered( machineryBreakdownPage.SPECIFIED_ITEMS_BASE_PREMIUM,
            "E020" );
        machineryBreakdownPage.setSpecifiedItemsBasePremium( "55" );
    }

    private void assertPremiumResetForBusinessLiability(
        PolicyDetailPage policyDetailsPage )
    {

        BusinessLiabilityPage businessLiabilityPage;
        businessLiabilityPage =
            performActivity( new NavigateToBusinessLiabilityPageActivity( policyDetailsPage ) );
        businessLiabilityPage.setNumberOfSituationsThisCoverAppliesTo( "3" );
        businessLiabilityPage.ruleTriggered( BusinessLiabilityPageXpath.BASE_PREMIUM,
            "E020" );
        businessLiabilityPage.setBasePremiumValue( "55" );

        businessLiabilityPage.setNumberOfWorkingProprietors( "3" );
        businessLiabilityPage.ruleTriggered( BusinessLiabilityPageXpath.BASE_PREMIUM,
            "E020" );
        businessLiabilityPage.setBasePremiumValue( "55" );

        businessLiabilityPage.setNumberOfWorkingEmployees( "3" );
        businessLiabilityPage.ruleTriggered( BusinessLiabilityPageXpath.BASE_PREMIUM,
            "E020" );
        businessLiabilityPage.setBasePremiumValue( "55" );

        businessLiabilityPage.setIsAircraftLandingExtensionRequired( "true" );
        businessLiabilityPage.ruleTriggered( BusinessLiabilityPageXpath.BASE_PREMIUM,
            "E020" );
        businessLiabilityPage.setBasePremiumValue( "55" );

        businessLiabilityPage.setIsIncomeDerived( "true" );
        businessLiabilityPage.setPercentageIncomeDerived( "10" );
        businessLiabilityPage.setActualIncomeDerived( "1500" );
        businessLiabilityPage.ruleTriggered( BusinessLiabilityPageXpath.BASE_PREMIUM,
            "E020" );

        businessLiabilityPage.setIsIncomeDerived( "false" );
        businessLiabilityPage.setBasePremiumValue( "55" );

        businessLiabilityPage.setIsPropertyLeased( "true" );
        businessLiabilityPage.ruleTriggered( BusinessLiabilityPageXpath.BASE_PREMIUM,
            "E020" );

        businessLiabilityPage.setIsPropertyLeased( "false" );
        businessLiabilityPage.setBasePremiumValue( "55" );
    }

    private void assertPremiumResetForBusinessInterruption( TheftPage theftPage )
    {

        BusinessInterruptionPage businessInterruptionPage;
        businessInterruptionPage =
            performActivity( new NavigateToBusinessInterruptionPageActivity( theftPage ) );
        businessInterruptionPage.setAgistmentIncomeSumInsured( "555" );
        businessInterruptionPage.setFarmContinuationExpensesSumInsured( "555" );

        businessInterruptionPage.ruleTriggered(
            BusinessInterruptionPageXpath.AGISTMENT_INCOME_BASE_PREMIUM,
            "E020" );

        businessInterruptionPage.ruleTriggered(
            BusinessInterruptionPageXpath.FARM_CONTINUATION_EXPENSES_BASE_PREMIUM,
            "E020" );

        businessInterruptionPage.setAgistmentBasePremium( "55" );
        businessInterruptionPage.setFarmContinuationBasePremium( "55" );
    }

    private void assertPremiumResetForTheft( FarmMachineryAndWorkingDogsPage farmMachineryPage )
    {
        TheftPage theftPage;
        theftPage =
            performActivity( new NavigateToTheftPageActivity( farmMachineryPage ) );
        theftPage.setFarmContentsValue( "555" );
        theftPage.ruleTriggered( theftPage.FARM_CONTENTS_AT_SITUATION_BASE_PREMIUM, "E020" );

        theftPage.setFarmContentsAtSituationBasePremium( "55" );
        theftPage.getSpecifiedFarmContentsGrid().deleteNameValueRow( 1 );
        theftPage.ruleTriggered( theftPage.FARM_CONTENTS_BASE_PREMIUM, "E020" );
        theftPage.setSpecifiedFarmContentsBasePremium( "55" );
        theftPage.getSpecifiedFarmMachineryGrid().deleteNameValueRow( 1 );
        theftPage.ruleTriggered( theftPage.FARM_MACHINERY_BASE_PREMIUM, "E020" );
        theftPage.setSpecifiedFarmMachineryBasePremium( "55" );
    }

    private void assertPremiumResetForFarmMachinery( HayFencesLiveStockPage hayFencesLiveStockPage )
    {
        FarmMachineryAndWorkingDogsPage farmMachineryPage;
        farmMachineryPage =
            performActivity( new NavigateToFarmMachineryPageActivity( hayFencesLiveStockPage ) );
        farmMachineryPage.setUnspecifiedFarmMachinerySumInsured( "555" );
        farmMachineryPage.ruleTriggered( farmMachineryPage.UNSPECIFIED_FARM_MACHINERY_BASE_PREMIUM,
            "E020" );
        farmMachineryPage.setUnspecifiedFarmMachineryBasePremium( "55" );

        farmMachineryPage.getFarmMachineryGrid().deleteNameValueRow( 1 );
        farmMachineryPage.ruleTriggered( farmMachineryPage.FARM_MACHINERY_BASE_PREMIUM,
            "E020" );
        farmMachineryPage.setFarmMachineryBasePremium( "55" );
        farmMachineryPage.getFarmDogsGrid().deleteNameValueRow( 1 );
        farmMachineryPage.ruleTriggered( farmMachineryPage.WORKING_DOGS_BASE_PREMIUM,
            "E020" );
        farmMachineryPage.setWorkingDogsBasePremium( "55" );
    }

    private DomesticBuildingAndContentsPage assertPremiumResetForDomesticBuilding(
        PolicyDetailPage policyDetailPage )
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage;
        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( policyDetailPage, 1 ) );
        domesticBuildingAndContentsPage.setAdditionalBusinessContentsSumInsured( "555" );
        domesticBuildingAndContentsPage.ruleTriggered(
            domesticBuildingAndContentsPage.ADDITIONAL_BUSINESS_CONTENTS_BASE_PREMIUM_LOCATOR,
            "E020" );
        domesticBuildingAndContentsPage.setAdditionalBusinessContentsBasePremium( "55" );

        return domesticBuildingAndContentsPage;
    }

    private void assertPremiumResetForHayFences( FarmPropertyPage farmPropertyPage )
    {
        HayFencesLiveStockPage hayFencesLiveStockPage;
        hayFencesLiveStockPage =
            performActivity( new NavigateToHayFencesPageActivity( farmPropertyPage ) );
        hayFencesLiveStockPage.setBoundaryNotSharedSumInsured( "555" );
        hayFencesLiveStockPage.ruleTriggered(
            hayFencesLiveStockPage.BOUNDARY_NOT_SHARED_BASE_PREMIUM,
            "E020" );
        hayFencesLiveStockPage.setBoundaryNotSharedBasePremium( "55" );
        hayFencesLiveStockPage.setSheepSumInsured( "555" );
        hayFencesLiveStockPage.ruleTriggered( hayFencesLiveStockPage.SHEEP_BASE_PREMIUM_LOCATOR,
            "E020" );
        hayFencesLiveStockPage.setSheepBasePremium( "55" );
        hayFencesLiveStockPage.setHayGrainSectionTotalSumInsured( "555" );
        hayFencesLiveStockPage.ruleTriggered( hayFencesLiveStockPage.HAY_AND_GRAIN_BASE_PREMIUM,
            "E020" );
        hayFencesLiveStockPage.setHayGrainBasePremium( "55" );
        hayFencesLiveStockPage.setFarmTreeSectionTotalSumInsured( "555" );
        hayFencesLiveStockPage.ruleTriggered( hayFencesLiveStockPage.FARM_TREE_BASE_PREMIUM,
            "E020" );
        hayFencesLiveStockPage.setFarmTreesBasePremium( "55" );
    }

    private void assertPremiumResetForFarmProperty(
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage )
    {
        FarmPropertyPage farmPropertyPage;
        farmPropertyPage =
            performActivity( new NavigateToFarmPropertyPageActivity(
                domesticBuildingAndContentsPage, 1 ) );

        farmPropertyPage.setUnspecifiedFarmBuildings( "555" );
        farmPropertyPage.ruleTriggered(
            farmPropertyPage.UNSPECIFIED_FARM_BUILDING_BASE_PREMIUM_LOCATOR,
            "E020" );
        farmPropertyPage.setUnspecifiedFarmBuildingBasePremium( "55" );

        farmPropertyPage.setAllFarmContentsLimit( "555" );
        farmPropertyPage.ruleTriggered( farmPropertyPage.IN_ANY_ONE_BUILDING_BASE_PREMIUM_LOCATOR,
            "E020" );
        farmPropertyPage.setInAnyOneBuildingBasePremium( "55" );

        farmPropertyPage.setAboveGroundFarmImprovements( "555" );
        farmPropertyPage.ruleTriggered(
            farmPropertyPage.ABOVE_GROUND_FARM_CONTENTS_BASE_PREMIUM_LOCATOR, "E020" );
        farmPropertyPage.setAboveGroundFarmImprovementsBasePremium( "55" );

        farmPropertyPage.getSpecifiedAboveGroundFarmImprovementsGrid().deleteNameValueRow( 1 );
        farmPropertyPage.ruleTriggered(
            farmPropertyPage.SPECIFIED_FARM_IMPROVEMENTS_BASE_PREMIUM_LOCATOR,
            "E020" );
        farmPropertyPage.setSpecifiedFarmImprovementsBasePremium( "55" );
    }

    private void suspendSaveAndEditThePolicy( RoadTransitPage roadTransitPage )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( roadTransitPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
    }
}
