package au.com.cgu.harvest.automation.scenario.countrypak;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.FinishPolicyForEndorsementActivity;
import au.com.cgu.harvest.automation.activity.FinishPolicyForEndorsementWithReferralActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStock2Activity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationWithReasonActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToSituationActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToPolicyDetailPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndAcceptActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.DeleteSituationPopup;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.ReadOnlyChecker;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "PH-1213, CFMC-1047" )
@Scenario( "Assert State Can be changed in an Endorsement Transaction for Countrypak Policy Scenario" )
public class AssertStateCodeCanBeChangedForCountrypakEndorsementsScenario extends AbstractScenario
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

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( situationLevelSectionPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity(
                insuranceHistoryPage ) );

        newBusinessPage =
            performActivity( new SaveAndAcceptActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        situationLevelSectionPage =
            performActivity( new NavigateToSituationActivity( policyDetailPage, 1 ) );

        ReadOnlyChecker.assertTextBoxesAreNotReadOnly( getWebDriver(), situationLevelSectionPage );

        situationLevelSectionPage =
            performActivity( new AddASituationActivity( situationLevelSectionPage ) );
        ReadOnlyChecker.checkNotReadOnly( getWebDriver(), situationLevelSectionPage );

        HayFencesLiveStockPage hayPage =
            performActivity( new AddHayFenceLiveStock2Activity( situationLevelSectionPage ) );

        DeleteSituationPopup deleteSituationPopup =
        		performActivity( new DeleteSituationWithReasonActivity( situationLevelSectionPage, 1 ) );
        policyDetailPage = deleteSituationPopup.setReason("Sale of Asset");

        policyDetailPage.setDutyOfDisclosure( "true" );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

        //Check for R065 - R065 refers as Hay/Fencing has been added to the new situation between 1/10 and 1/4
        LocalDate date = new LocalDate();
        if ( date.getMonthOfYear() >= 10 || date.getMonthOfYear() <= 3 )
        {
            OutstandingReferralsPopup referralPopup =
                performActivity( new FinishPolicyForEndorsementWithReferralActivity( finishPage ) );
            newBusinessPage =
                performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        }
        else
        {
            newBusinessPage =
                performActivity( new FinishPolicyForEndorsementActivity(
                    finishPage ) );
        }

        newBusinessPage =
            performActivity( new SaveAndAcceptActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        addSituationWithDifferentState( situationLevelSectionPage );

        policyDetailPage =
            performActivity( new NavigateToPolicyDetailPageActivity( situationLevelSectionPage ) );
        policyDetailPage.setDutyOfDisclosure( "true" );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

        date = new LocalDate();
        if ( date.getMonthOfYear() >= 10 || date.getMonthOfYear() <= 3 )
        {
            OutstandingReferralsPopup referralPopup =
                performActivity( new FinishPolicyForEndorsementWithReferralActivity( finishPage ) );
            newBusinessPage =
                performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        }
        else
        {
            newBusinessPage =
                performActivity( new FinishPolicyForEndorsementActivity(
                    finishPage ) );
        }

        newBusinessPage =
            performActivity( new SaveAndAcceptActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        // This is as part of CFMC-1047
        // assertDeleteDoesNotWork( policyDetailPage );

    }

    private void assertDeleteDoesNotWork( PolicyDetailPage policyDetailPage )
    {
        SituationDetailPage situationPage =
            policyDetailPage.getNavigationTree().navigateToSituation( 1 );

        List< WebElement > matchingElements;
        matchingElements =
            getWebDriver()
                .findElements(
                    By.xpath( "//div[@role='treeitem']/a/span[text()='Situation Level Sections']/ancestor::table//div/following::div[2]/div[1]" ) );
        for ( WebElement element : matchingElements )
        {
            Assert.assertFalse( element.getAttribute( "class" ).contains( "treeItemDeleteAction" ) );
        }

        // situationPage.isDeleteDisabled();
    }

    private void addSituationWithDifferentState( SituationDetailPage situationLevelSectionPage )
    {
        SituationDetailPage addSituation =
            situationLevelSectionPage.getToolbar().addSituation();
        addSituation.setAddressLine1( "Situation-3" );
        addSituation.setSuburbStatePostcode( "Ballarat", "VIC", "3350" );
        addSituation.setPropertySize( "2000" );
        addSituation.setAnnualTurnOver( "100000" );
        addSituation.setOccupation( "Pig Farming (0151)" );
        ReadOnlyChecker.checkNotReadOnly( getWebDriver(), situationLevelSectionPage );

        HayFencesLiveStockPage hayPage =
            performActivity( new AddHayFenceLiveStock2Activity( situationLevelSectionPage ) );
    }
}
