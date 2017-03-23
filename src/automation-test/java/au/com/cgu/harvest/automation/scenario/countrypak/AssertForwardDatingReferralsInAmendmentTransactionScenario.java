package au.com.cgu.harvest.automation.scenario.countrypak;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ModifyStartDateToCheckReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ViewRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "CFMC-629" )
@Scenario( "Assert Forward Dating referrals in subsequent Amendment Transaction Scenario" )
public class AssertForwardDatingReferralsInAmendmentTransactionScenario extends AbstractScenario
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

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( policyDetailPage ) );

        policyDetailPage =
            performActivity( new ModifyStartDateToCheckReferralActivity( newBusinessPage,
                new LocalDate().plusMonths( 3 ).toString( "dd/MM/yyyy" ), "R058" ) );

        SituationDetailPage situationLevelSectionPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                insuranceHistoryPage ) );
        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( situationLevelSectionPage ) );

        FinishPage finishPage;
        OutstandingReferralsPopup referralPopup;
        Wait< WebDriver > wait;
        policyDetailPage = viewReferralInNBTransaction( roadTransitPage );

        newBusinessPage = policyDetailPage.getToolbar().close();

        viewReferralInAmendmentTransaction( newBusinessPage );

        viewReferralWhenFinishedAsInForcePolicy( roadTransitPage );
    }

    private void viewReferralWhenFinishedAsInForcePolicy( RoadTransitPage roadTransitPage )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage;
        FinishPage finishPage;
        OutstandingReferralsPopup referralPopup;
        Wait< WebDriver > wait;
        finishPage = roadTransitPage.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "In Force" );
        finishPage.setPolicyDeclaration( "Yes" );
        finishPage.setPremiumPaymentMethod( "Intermediary Statement" );
        referralPopup = finishPage.finishWithReferral();

        newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ViewRiskDetailsActivity( newBusinessPage ) );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( policyDetailPage.getNavigationTree().POLICY_DETAIL_WITH_REFERRAL_LOCATOR ),
            "R058" ) );
    }

    private void viewReferralInAmendmentTransaction( NewBusinessPage newBusinessPage )
    {
        PolicyDetailPage policyDetailPage;
        Wait< WebDriver > wait;
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( policyDetailPage.getNavigationTree().POLICY_DETAIL_WITH_REFERRAL_LOCATOR ),
            "R058" ) );
    }

    private PolicyDetailPage viewReferralInNBTransaction( RoadTransitPage roadTransitPage )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage;
        FinishPage finishPage = roadTransitPage.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Covernote" );
        finishPage.setPrintedDeclaration( "No" );
        OutstandingReferralsPopup referralPopup = finishPage.finishWithReferral();

        newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ViewRiskDetailsActivity( newBusinessPage ) );

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( policyDetailPage.getNavigationTree().POLICY_DETAIL_WITH_REFERRAL_LOCATOR ),
            "R058" ) );
        return policyDetailPage;
    }
}
