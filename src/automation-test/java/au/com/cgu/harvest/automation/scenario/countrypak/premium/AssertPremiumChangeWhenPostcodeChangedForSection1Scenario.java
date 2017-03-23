package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationWithNoAnnualTurnoverActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AssertPremiumsInDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToSituationLevelSectionPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Premium Changes in Section 1 - Domestic Building When Postcode is Changed for a Situation Scenario" )
public class AssertPremiumChangeWhenPostcodeChangedForSection1Scenario extends AbstractScenario
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

        DomesticBuildingAndContentsPage domesticBuildingPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );
        domesticBuildingPage =
            performActivity( new AddDwellingType( domesticBuildingPage ) );

        domesticBuildingPage = verifyPremiumChangesWhenPostCodeChanges( domesticBuildingPage );

        domesticBuildingPage =
            verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( domesticBuildingPage );

        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 1 ) );

        situationLevelSectionPage =
            performActivity( new AddASituationWithNoAnnualTurnoverActivity(
                insuranceHistoryPage ) );
        verifyNoPremiumForNilAnnualTurnOver( domesticBuildingPage );

    }

    private void verifyNoPremiumForNilAnnualTurnOver(
        DomesticBuildingAndContentsPage domesticBuildingPage )
    {
        SituationDetailPage situationLevelSectionPage;
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( domesticBuildingPage ) );

        domesticBuildingPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( situationLevelSectionPage,
                1 ) );
        domesticBuildingPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );
        domesticBuildingPage =
            performActivity( new AddDwellingType( domesticBuildingPage ) );
        domesticBuildingPage =
            performActivity( new AssertPremiumsInDomesticBuildingActivity( domesticBuildingPage,
                "", "", "", "", "" ) );
    }

    private DomesticBuildingAndContentsPage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy(
        DomesticBuildingAndContentsPage domesticBuildingPage )
    {
        PolicyDetailPage policyDetailPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( domesticBuildingPage ) );
        Premium domesticBuilding = premiumPage.getPremiumForRow( 1 );
        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( domesticBuildingPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        premiumPage = performActivity( new ViewPremiumActivity( domesticBuildingPage ) );
        Premium domesticBuildingAfterSuspend = premiumPage.getPremiumForRow( 1 );
        PremiumAssert.assertTotalPremiumUnchanged( domesticBuilding, domesticBuildingAfterSuspend );
        return domesticBuildingPage;
    }

    private DomesticBuildingAndContentsPage verifyPremiumChangesWhenPostCodeChanges(
        DomesticBuildingAndContentsPage domesticBuildingPage )
    {
        SituationDetailPage situationLevelSectionPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( domesticBuildingPage ) );
        Premium domesticBuilding = premiumPage.getPremiumForRow( 1 );
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( domesticBuildingPage ) );
        situationLevelSectionPage.setSuburbStatePostcode( "Ballarat", "VIC", "3350" );
        premiumPage = performActivity( new ViewPremiumActivity( domesticBuildingPage ) );
        Premium domesticBuildingAfterPostcodeChange = premiumPage.getPremiumForRow( 1 );
        PremiumAssert.assertTotalPremiumChanged( domesticBuilding,
            domesticBuildingAfterPostcodeChange );
        return domesticBuildingPage;
    }
}
