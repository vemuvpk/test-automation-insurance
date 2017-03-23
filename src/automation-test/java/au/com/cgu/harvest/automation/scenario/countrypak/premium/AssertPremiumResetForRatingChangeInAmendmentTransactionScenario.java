package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

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
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToRoadTransitPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToTheftPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Rule;
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
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "CFMC-494" )
@Scenario( "Assert Manual Premium is Reset for change in rating Factor In an Amendment Transaction for a Countrypak Policy Scenario" )
public class AssertPremiumResetForRatingChangeInAmendmentTransactionScenario extends
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
            performActivity( new CreateASituationForRaedownActivity(
                insuranceHistoryPage ) );

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingWithManualPremiumActivity(
                situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingTypeWithManualPremium( domesticBuildingAndContentsPage )
            );

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyWithManualPremiumActivity(
                situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingTypeWithManualPremiumActivity( farmPropertyPage ) );

        FarmMachineryAndWorkingDogsPage farmMachineryPage =
            performActivity( new AddFarmMachineryWithManualPremiumActivity(
                situationLevelSectionPage ) );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithManualPremiumActivity(
                situationLevelSectionPage ) );

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStockWithManualPremiumActivity( farmMachineryPage )
            );

        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new AddBusinessInterruptionWithManualPremiumActivity(
                situationLevelSectionPage ) );

        BusinessLiabilityPage businessLiabilityPage =
            performActivity( new AddBusinessLiabilityWithManualPremiumActivity(
                situationLevelSectionPage ) );

        MachineryBreakdownPage machineryBreakdownPage =
            performActivity( new AddMachineryBreakdownWithManualPremiumActivity(
                situationLevelSectionPage ) );

        PersonalIncomePage personalIncomePage =
            performActivity( new AddPersonalIncomeWithManualPremiumActivity(
                situationLevelSectionPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitWithManualPremiumActivity( situationLevelSectionPage ) );

        doAnAmendmentTransactionActivity( roadTransitPage );

        domesticBuildingAndContentsPage =
            assertPremiumResetForDomesticBuilding( policyDetailPage );

        assertPremiumResetForFarmProperty( domesticBuildingAndContentsPage );

        assertPremiumResetForHayFences( farmPropertyPage );

        assertPremiumResetForFarmMachinery( hayFencesLiveStockPage );

        assertPremiumResetForTheft( farmMachineryPage );

        assertPremiumResetForBusinessInterruption( theftPage );

        assertPremiumResetForBusinessLiability( policyDetailPage );

        assertPremiumResetForMachineryBreakdown( businessLiabilityPage );

        assertPremiumResetForPersonalIncome( machineryBreakdownPage );

        assertPremiumResetForRoadTransit( personalIncomePage );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( roadTransitPage ) );

        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyAsNewPolicyWithReferralActivity( finishPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

    }

    private void assertPremiumResetForRoadTransit( PersonalIncomePage personalIncomePage )
    {
        RoadTransitPage roadTransitPage;
        roadTransitPage =
            performActivity( new NavigateToRoadTransitPageActivity( personalIncomePage ) );
        roadTransitPage.setSumInsured( "5000" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( roadTransitPage.BASE_PREMIUM ), "E020" ) );
        roadTransitPage.setBasePremium( "55" );
    }

    private void assertPremiumResetForPersonalIncome( MachineryBreakdownPage machineryBreakdownPage )
    {
        PersonalIncomePage personalIncomePage;
        personalIncomePage =
            performActivity( new NavigateToPersonalIncomePageActivity( machineryBreakdownPage ) );
        personalIncomePage.setDateOfBirth( "1/01/84" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ACCIDENT_BASE_PREMIUM_LOCATOR ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR ), "E020" ) );
        personalIncomePage.setAccidentBasePremium( "55" );
        personalIncomePage.setIllnessBasePremium( "55" );

        personalIncomePage.setGender( "Female" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ACCIDENT_BASE_PREMIUM_LOCATOR ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR ), "E020" ) );
        personalIncomePage.setAccidentBasePremium( "55" );
        personalIncomePage.setIllnessBasePremium( "55" );

        personalIncomePage.setNumberOfAccidentUnits( "4" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ACCIDENT_BASE_PREMIUM_LOCATOR ), "E020" ) );
        personalIncomePage.setAccidentBasePremium( "55" );

        personalIncomePage.setNumberOfIllnessUnits( "2" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR ), "E020" ) );
        personalIncomePage.setIllnessBasePremium( "55" );

        personalIncomePage.setDoYouWishToReducePremium( "true" );
        personalIncomePage.setNominateNumberOfWeeks( "2" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ACCIDENT_BASE_PREMIUM_LOCATOR ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR ), "E020" ) );
        personalIncomePage.setAccidentBasePremium( "55" );
        personalIncomePage.setIllnessBasePremium( "55" );

        personalIncomePage.setAreYouNonSmoker( "false" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR ), "E020" ) );
        personalIncomePage.setIllnessBasePremium( "55" );
    }

    private void assertPremiumResetForMachineryBreakdown(
        BusinessLiabilityPage businessLiabilityPage )
    {
        MachineryBreakdownPage machineryBreakdownPage;
        machineryBreakdownPage =
            performActivity( new NavigateToMachineryBreakdownPageActivity( businessLiabilityPage ) );
        machineryBreakdownPage.setBlanketCoverRatingUnits( "4" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( machineryBreakdownPage.BLANKET_COVER_BASE_PREMIUM ), "E020" ) );
        machineryBreakdownPage.setBlanketCoverBasePremium( "55" );

        machineryBreakdownPage.setSpecifiedItemsRatingUnits( "4" );
        wait.until( Rule.isDisplayed(
            By.xpath( machineryBreakdownPage.SPECIFIED_ITEMS_BASE_PREMIUM ), "E020" ) );
        machineryBreakdownPage.setSpecifiedItemsBasePremium( "55" );

        machineryBreakdownPage.getSpecifiedItemsGrid().deleteNameValueRow( 1 );
        wait.until( Rule.isDisplayed(
            By.xpath( machineryBreakdownPage.SPECIFIED_ITEMS_BASE_PREMIUM ), "E020" ) );
        machineryBreakdownPage.setSpecifiedItemsBasePremium( "55" );
    }

    private void assertPremiumResetForBusinessLiability(
        PolicyDetailPage policyDetailsPage )
    {

        BusinessLiabilityPage businessLiabilityPage;
        businessLiabilityPage =
            performActivity( new NavigateToBusinessLiabilityPageActivity( policyDetailsPage ) );
        businessLiabilityPage.setNumberOfSituationsThisCoverAppliesTo( "3" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
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
        businessInterruptionPage.setAgistmentBasePremium( "55" );

        businessInterruptionPage.setFarmContinuationExpensesSumInsured( "555" );
        businessInterruptionPage.ruleTriggered(
            BusinessInterruptionPageXpath.FARM_CONTINUATION_EXPENSES_BASE_PREMIUM,
            "E020" );
        businessInterruptionPage.setFarmContinuationBasePremium( "55" );
    }

    private void assertPremiumResetForTheft( FarmMachineryAndWorkingDogsPage farmMachineryPage )
    {
        TheftPage theftPage;
        theftPage =
            performActivity( new NavigateToTheftPageActivity( farmMachineryPage ) );
        theftPage.setFarmContentsValue( "555" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( theftPage.FARM_CONTENTS_AT_SITUATION_BASE_PREMIUM ), "E020" ) );
        theftPage.setFarmContentsAtSituationBasePremium( "55" );
        theftPage.getSpecifiedFarmContentsGrid().deleteNameValueRow( 1 );
        wait.until( Rule.isDisplayed(
            By.xpath( theftPage.FARM_CONTENTS_BASE_PREMIUM ), "E020" ) );
        theftPage.setSpecifiedFarmContentsBasePremium( "55" );
        theftPage.getSpecifiedFarmMachineryGrid().deleteNameValueRow( 1 );
        wait.until( Rule.isDisplayed(
            By.xpath( theftPage.FARM_MACHINERY_BASE_PREMIUM ), "E020" ) );
        theftPage.setSpecifiedFarmMachineryBasePremium( "55" );
    }

    private void assertPremiumResetForFarmMachinery( HayFencesLiveStockPage hayFencesLiveStockPage )
    {
        FarmMachineryAndWorkingDogsPage farmMachineryPage;
        farmMachineryPage =
            performActivity( new NavigateToFarmMachineryPageActivity( hayFencesLiveStockPage ) );
        farmMachineryPage.setUnspecifiedFarmMachinerySumInsured( "555" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmMachineryPage.UNSPECIFIED_FARM_MACHINERY_BASE_PREMIUM ),
            "E020" ) );
        farmMachineryPage.setUnspecifiedFarmMachineryBasePremium( "55" );

        farmMachineryPage.getFarmMachineryGrid().deleteNameValueRow( 1 );
        wait.until( Rule.isDisplayed(
            By.xpath( farmMachineryPage.FARM_MACHINERY_BASE_PREMIUM ),
            "E020" ) );
        farmMachineryPage.setFarmMachineryBasePremium( "55" );
        farmMachineryPage.getFarmDogsGrid().deleteNameValueRow( 1 );
        wait.until( Rule.isDisplayed(
            By.xpath( farmMachineryPage.WORKING_DOGS_BASE_PREMIUM ),
            "E020" ) );
        farmMachineryPage.setWorkingDogsBasePremium( "55" );
    }

    private DomesticBuildingAndContentsPage assertPremiumResetForDomesticBuilding(
        PolicyDetailPage policyDetailPage )
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage;
        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( policyDetailPage, 1 ) );
        domesticBuildingAndContentsPage.setAdditionalBusinessContentsSumInsured( "555" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( domesticBuildingAndContentsPage.ADDITIONAL_BUSINESS_CONTENTS_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        domesticBuildingAndContentsPage.setAdditionalBusinessContentsBasePremium( "55" );

        return domesticBuildingAndContentsPage;
    }

    private void assertPremiumResetForHayFences( FarmPropertyPage farmPropertyPage )
    {
        HayFencesLiveStockPage hayFencesLiveStockPage;
        hayFencesLiveStockPage =
            performActivity( new NavigateToHayFencesPageActivity( farmPropertyPage ) );
        hayFencesLiveStockPage.setBoundaryNotSharedSumInsured( "555" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.BOUNDARY_NOT_SHARED_BASE_PREMIUM ),
            "E020" ) );
        hayFencesLiveStockPage.setBoundaryNotSharedBasePremium( "55" );
        hayFencesLiveStockPage.setSheepSumInsured( "555" );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.SHEEP_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        hayFencesLiveStockPage.setSheepBasePremium( "55" );
        hayFencesLiveStockPage.setHayGrainSectionTotalSumInsured( "555" );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.HAY_AND_GRAIN_BASE_PREMIUM ),
            "E020" ) );
        hayFencesLiveStockPage.setHayGrainBasePremium( "55" );
        hayFencesLiveStockPage.setFarmTreeSectionTotalSumInsured( "555" );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.FARM_TREE_BASE_PREMIUM ),
            "E020" ) );
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
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.UNSPECIFIED_FARM_BUILDING_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        farmPropertyPage.setUnspecifiedFarmBuildingBasePremium( "55" );

        farmPropertyPage.setAllFarmContentsLimit( "555" );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.IN_ANY_ONE_BUILDING_BASE_PREMIUM_LOCATOR ), "E020" ) );
        farmPropertyPage.setInAnyOneBuildingBasePremium( "55" );

        farmPropertyPage.setAboveGroundFarmImprovements( "555" );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.ABOVE_GROUND_FARM_CONTENTS_BASE_PREMIUM_LOCATOR ), "E020" ) );
        farmPropertyPage.setAboveGroundFarmImprovementsBasePremium( "55" );

        farmPropertyPage.getSpecifiedAboveGroundFarmImprovementsGrid().deleteNameValueRow( 1 );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.SPECIFIED_FARM_IMPROVEMENTS_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        farmPropertyPage.setSpecifiedFarmImprovementsBasePremium( "55" );
    }

    private void doAnAmendmentTransactionActivity( HarvestPage page )
    {
        PolicyDetailPage policyDetailPage;
        RoadTransitPage roadTransitPage;
        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( page ) );

        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyAsCoverNoteWithReferralActivity( finishPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
    }

}
