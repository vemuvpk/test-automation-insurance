package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddMachineryBreakdownWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPersonalIncomeWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelDomesticBuildingWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelFarmPropertyWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithManualPremiumActivity;
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
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForRaedownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessInterruptionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessLiabilityPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmMachineryPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmPropertyPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToMachineryBreakdownPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPersonalIncomePageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPolicyLevelDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPolicyLevelFarmPropertyPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToRoadTransitPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToSituationLevelSectionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToTheftPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
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
@Scenario( "Assert Manually Rated Policy Remains Manually rated in Endorsement Scenario" )
public class AssertManualPolicyRemainsManualForEndorsementScenario extends AbstractScenario
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
                situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingTypeWithManualPremiumActivity( farmPropertyPage ) );

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceWithManualPremiumActivity( farmPropertyPage ) );

        PolicyLevelDomesticBuildingPage policyLevelDomesticBuilding =
            performActivity( new AddPolicyLevelDomesticBuildingWithManualPremiumActivity(
                hayFencesLiveStockPage ) );

        PolicyLevelFarmPropertyPage policyLevelFarmPropertypage =
            performActivity( new AddPolicyLevelFarmPropertyWithManualPremiumActivity(
                policyLevelDomesticBuilding ) );

        FarmMachineryAndWorkingDogsPage farmmachinery =
            performActivity( new AddFarmMachineryWithManualPremiumActivity(
                policyLevelFarmPropertypage ) );

        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new AddBusinessInterruptionWithManualPremiumActivity(
                farmmachinery ) );

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

        TheftPage theftPage =
            performActivity( new AddTheftPageWithManualPremiumActivity(
                roadTransitPage ) );

        doAnEndorsementTransactionActivity( theftPage );

        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( theftPage ) );
        ( ( JavascriptExecutor ) getWebDriver() )
            .executeScript( "window.confirm = function(msg){return true;};" );
        situationLevelSectionPage.setSuburbStatePostcode( "Orange", "NSW", "2800" );

        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( situationLevelSectionPage,
                1 ) );
        domesticBuildingAndContentsPage =
            performActivity( new AssertErrorRulesForManualDomesticBuildingActivity(
                domesticBuildingAndContentsPage ) );
        domesticBuildingAndContentsPage =
            performActivity( new AssertReferralForManualDomesticBuildingActivity(
                domesticBuildingAndContentsPage ) );

        farmPropertyPage =
            performActivity( new NavigateToFarmPropertyPageActivity(
                situationLevelSectionPage, 1 ) );
        farmPropertyPage =
            performActivity( new AssertErrorRulesForManualFarmPropertyActivity( farmPropertyPage ) );
        farmPropertyPage =
            performActivity( new AssertReferralForManualFarmPropertyActivity( farmPropertyPage ) );

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

        hayFencesLiveStockPage =
            farmPropertyPage.getNavigationTree().navigateToHayFenceLiveStock( 1 );

        hayFencesLiveStockPage =
            performActivity( new AssertErrorRulesForManualHayFencesActivity( hayFencesLiveStockPage )
            );
        hayFencesLiveStockPage =
            performActivity( new AssertReferralForManualHayFencesActivity( hayFencesLiveStockPage )
            );

        farmmachinery =
            performActivity( new NavigateToFarmMachineryPageActivity( situationLevelSectionPage ) );
        farmmachinery =
            performActivity( new AssertErrorRulesForManualFarmMachineryPageActivity( farmmachinery )
            );
        farmmachinery =
            performActivity( new AssertReferralForManualFarmMachineryPageActivity( farmmachinery ) );

        businessInterruptionPage =
            performActivity( new NavigateToBusinessInterruptionPageActivity(
                situationLevelSectionPage ) );
        businessInterruptionPage =
            performActivity( new AssertErrorRulesForManualBusinessInterruptionActivity(
                businessInterruptionPage ) );
        businessInterruptionPage =
            performActivity( new AssertReferralForManualBusinessInterruptionActivity(
                businessInterruptionPage ) );

        businessLiabilityPage =
            performActivity( new NavigateToBusinessLiabilityPageActivity(
                situationLevelSectionPage ) );
        businessLiabilityPage =
            performActivity( new AssertErrorRulesForManualBusinessLiabilityActivity(
                businessLiabilityPage ) );
        businessLiabilityPage =
            performActivity( new AssertReferralForManualBusinessLiabilityActivity(
                businessLiabilityPage ) );

        machineryBreakdownPage =
            performActivity( new NavigateToMachineryBreakdownPageActivity(
                situationLevelSectionPage ) );
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

        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyAsNewPolicyWithReferralActivity( finishPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
    }

}
