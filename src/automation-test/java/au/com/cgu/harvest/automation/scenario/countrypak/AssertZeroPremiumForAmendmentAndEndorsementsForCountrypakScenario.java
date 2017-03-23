package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AssertAndSetDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingTypeWithManualPremium;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForRaedownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToRoadTransitPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToPolicyDetailPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert a policy with Zero premium value in Amendment and Endorsement Transactions for Countrypak Scenario" )
public class AssertZeroPremiumForAmendmentAndEndorsementsForCountrypakScenario extends
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

        DomesticBuildingAndContentsPage domesticBuildingPage =
            performActivity( new AddDomesticBuildingWithManualPremiumActivity(
                situationLevelSectionPage ) );
        domesticBuildingPage =
            performActivity( new AddDwellingTypeWithManualPremium( domesticBuildingPage ) );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithManualPremiumActivity( domesticBuildingPage ) );

        // Check Negative premium is allowed in amendment transaction
        policyDetailPage = doAnAmendmentTransaction( insuranceHistoryPage, theftPage );
        RoadTransitPage roadTransitPage;
        policyDetailPage = assertNegativePremiumRuleForNewRisk( policyDetailPage );
        assertNegativePremiumAcceptedInTheTransaction( policyDetailPage, "100", "0.0" );

        roadTransitPage =
            performActivity( new NavigateToRoadTransitPageActivity( policyDetailPage ) );
        policyDetailPage =
            performActivity( new DeleteRoadTransitActivity( roadTransitPage ) );

        // Check Negative premium is allowed in Endorsement transaction
        doAnEndorsementTransaction( policyDetailPage );
        assertNegativePremiumAcceptedInTheTransaction( policyDetailPage, "50", "0" );

    }

    private PolicyDetailPage doAnAmendmentTransaction(
        CountrypakInsuranceHistoryPage insuranceHistoryPage, TheftPage theftPage )
    {
        PolicyDetailPage policyDetailPage;
        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( theftPage ) );

        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyAsCoverNoteWithReferralActivity(
                insuranceHistoryPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        return policyDetailPage;
    }

    private void doAnEndorsementTransaction( PolicyDetailPage policyDetailPage )
    {
        FinishPage finishPage;
        OutstandingReferralsPopup referralPopup;
        NewBusinessPage newBusinessPage;
        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        referralPopup =
            performActivity( new FinishPolicyAsNewPolicyWithReferralActivity( finishPage ) );
        newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
    }

    private void assertNegativePremiumAcceptedInTheTransaction( PolicyDetailPage policyDetailPage,
        String value, String premium )
    {
        String _value = value;
        String _premium = premium;

        DomesticBuildingAndContentsPage domesticBuildingPage;
        domesticBuildingPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( policyDetailPage, 1 ) );
        domesticBuildingPage.setAdditionalBusinessContentsSumInsured( _value );

        domesticBuildingPage.setAdditionalBusinessContentsBasePremium( _premium );
        // assert R062
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( domesticBuildingPage.ADDITIONAL_BUSINESS_CONTENTS_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
    }

    private PolicyDetailPage assertNegativePremiumRuleForNewRisk( PolicyDetailPage policyDetailPage )
    {
        RoadTransitPage roadTransitPage =
            performActivity( new NavigateToRoadTransitPageActivity( policyDetailPage ) );
        roadTransitPage.setSumInsured( "10000" );
        roadTransitPage.setBasePremium( "-150.00" );
        // chk for E010
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( roadTransitPage.BASE_PREMIUM ), "E010" ) );
        policyDetailPage =
            performActivity( new NavigateToPolicyDetailPageActivity( roadTransitPage ) );
        policyDetailPage =
            performActivity( new AssertAndSetDutyOfDisclosureActivity( policyDetailPage ) );
        roadTransitPage =
            performActivity( new NavigateToRoadTransitPageActivity( policyDetailPage ) );
        roadTransitPage.setBasePremium( "150.00" );
        return policyDetailPage;
    }
}
