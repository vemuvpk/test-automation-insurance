package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AssertAndSetDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessLiabilityActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStock2Activity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPersonalIncomeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithFarmContentsAndFarmMachinery;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmMachineryPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmPropertyPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToPolicyDetailPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Change Countrypak risk Details as part of an Amendment Scenario" )
public class ChangeRiskDetailsInAmendmentForCountrypakPolicyScenario extends AbstractScenario
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
                insuranceHistoryPage ) );

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationDetailPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );

        FarmMachineryAndWorkingDogsPage farmMachineryPage =
            performActivity( new AddFarmMachineryActivity( domesticBuildingAndContentsPage ) );

        PersonalIncomePage personalIncomePage =
            performActivity( new AddPersonalIncomeActivity( farmMachineryPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( personalIncomePage ) );

        policyDetailPage = createAnAmendmentTransaction( policyDetailPage );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithFarmContentsAndFarmMachinery( policyDetailPage ) );
        BusinessLiabilityPage liabilityPage =
            performActivity( new AddBusinessLiabilityActivity( theftPage ) );

        farmMachineryPage =
            performActivity( new NavigateToFarmMachineryPageActivity( liabilityPage ) );
        policyDetailPage =
            performActivity( new DeleteFarmMachineryActivity( farmMachineryPage ) );
        assertForDeclinedRuleInPolicyDetailPage( policyDetailPage );
        policyDetailPage.setDutyOfDisclosure( "true" );

        policyDetailPage = createAnAmendmentTransaction( policyDetailPage );

        // Add a Domestic building and assert for Duty of Disclosure
        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( policyDetailPage, 1 ) );
        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );
        checkDutyOfDiscloseureAndAmendThePolicy( domesticBuildingAndContentsPage );

        // Add New Section and assert for Duty of Disclosure
        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationDetailPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );
        checkDutyOfDiscloseureAndAmendThePolicy( domesticBuildingAndContentsPage );

        // Add New Farm Building and assert for Duty of Disclosure
        farmPropertyPage =
            performActivity( new NavigateToFarmPropertyPageActivity( policyDetailPage, 1 ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );
        checkDutyOfDiscloseureAndAmendThePolicy( farmPropertyPage );

        // Add New Situation and assert for Duty of Disclosure
        situationDetailPage =
            performActivity( new AddASituationActivity( situationDetailPage ) );

        HayFencesLiveStockPage hayFencesPage =
            performActivity( new AddHayFenceLiveStock2Activity( situationDetailPage ) );

        checkDutyOfDiscloseureAndAmendThePolicy( hayFencesPage );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity(
                finishPage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
    }

    private void checkDutyOfDiscloseureAndAmendThePolicy( HarvestPage page
        )
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage;
        PolicyDetailPage policyDetailPage;
        policyDetailPage =
            performActivity( new NavigateToPolicyDetailPageActivity(
                page ) );
        policyDetailPage =
            performActivity( new AssertAndSetDutyOfDisclosureActivity( policyDetailPage ) );
        policyDetailPage = createAnAmendmentTransaction( policyDetailPage );
    }

    private PolicyDetailPage createAnAmendmentTransaction( PolicyDetailPage policyDetailPage )
    {
        FinishPage finishPage;
        NewBusinessPage newBusinessPage;
        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

        newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity(
                finishPage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        return policyDetailPage;
    }

    private void assertForDeclinedRuleInPolicyDetailPage( PolicyDetailPage policyDetailPage )
    {
        policyDetailPage =
            performActivity( new NavigateToPolicyDetailPageActivity( policyDetailPage ) );

        policyDetailPage.setDutyOfDisclosure( "false" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( policyDetailPage.DUTY_OF_DISCLOSURE_LOCATOR ),
            "D004" ) );
    }
}
