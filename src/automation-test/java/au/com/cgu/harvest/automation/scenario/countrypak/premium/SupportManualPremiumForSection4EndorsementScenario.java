package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertPremiumsInTheftPageForEndorsementActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForRaedownActivity;
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
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Support Manual Premium in Section 4-Theft For Endorsement Transaction Scenario" )
public class SupportManualPremiumForSection4EndorsementScenario extends AbstractScenario
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

        TheftPage theftPage =
            performActivity( new AddTheftPageWithManualPremiumActivity(
                situationLevelSectionPage ) );

        doAnAmendmentTransactionActivity( theftPage );

        theftPage =
            performActivity( new NavigateToTheftPageActivity( policyDetailPage ) );

        theftPage.getSpecifiedFarmContentsGrid().deleteNameValueRow( 2 );
        theftPage.setSpecifiedFarmContentsBasePremium( "65.66" );

        doAnEndorsementTransactionActivity( theftPage );

        editFarmContentsToCheckForErrorInTheBasePremiumField( policyDetailPage );

        verifyNilPremium( theftPage );
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

    private void doAnAmendmentTransactionActivity( HarvestPage page )
    {
        PolicyDetailPage policyDetailPage;
        TheftPage theftPage;
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

    private void verifyNilPremium( TheftPage theftPage )
    {
        theftPage =
            performActivity( new NavigateToTheftPageActivity( theftPage ) );
        theftPage =
            performActivity( new AssertPremiumsInTheftPageForEndorsementActivity( theftPage, "",
                "0.00", "0.00", "" ) );
    }

}
