package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationWithNoAnnualTurnoverActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertPremiumsInHayFencesActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToHayFencesPageActivity;
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
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Premium Changes in Section 5 When Postcode is Changed for a Situation Scenario" )
public class AssertPremiumChangeWhenPostcodeChangedForSection5Scenario extends AbstractScenario
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

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStockActivity( situationLevelSectionPage ) );

        hayFencesLiveStockPage = verifyPremiumChangesWhenPostcodeChanges( hayFencesLiveStockPage );

        hayFencesLiveStockPage =
            verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( hayFencesLiveStockPage );

        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 1 ) );

        situationLevelSectionPage =
            performActivity( new AddASituationWithNoAnnualTurnoverActivity(
                insuranceHistoryPage ) );
        verifyNilPremiumForNilAnnualTurnover( hayFencesLiveStockPage );
    }

    private void verifyNilPremiumForNilAnnualTurnover( HayFencesLiveStockPage hayFencesLiveStockPage )
    {
        SituationDetailPage situationLevelSectionPage;
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                hayFencesLiveStockPage ) );

        hayFencesLiveStockPage =
            performActivity( new NavigateToHayFencesPageActivity( situationLevelSectionPage ) );
        hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStockActivity( situationLevelSectionPage ) );
        hayFencesLiveStockPage =
            performActivity( new AssertPremiumsInHayFencesActivity( hayFencesLiveStockPage, "", "",
                "", "", "", "", "" ) );
    }

    private HayFencesLiveStockPage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy(
        HayFencesLiveStockPage hayFencesLiveStockPage )
    {
        PolicyDetailPage policyDetailPage;
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( hayFencesLiveStockPage ) );
        Premium hayFences = premiumPage.getPremiumForRow( 1 );
        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( hayFencesLiveStockPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        premiumPage =
            performActivity( new ViewPremiumActivity( hayFencesLiveStockPage ) );
        Premium hayFencesAfterSuspend = premiumPage.getPremiumForRow( 1 );
        PremiumAssert.assertTotalPremiumUnchanged( hayFences, hayFencesAfterSuspend );

        return hayFencesLiveStockPage;
    }

    private HayFencesLiveStockPage verifyPremiumChangesWhenPostcodeChanges(
        HayFencesLiveStockPage hayFencesLiveStockPage )
    {
        SituationDetailPage situationLevelSectionPage;
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( hayFencesLiveStockPage ) );
        Premium hayFences = premiumPage.getPremiumForRow( 1 );

        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                hayFencesLiveStockPage ) );

        situationLevelSectionPage.setSuburbStatePostcode( "Ballarat", "VIC", "3350" );
        premiumPage =
            performActivity( new ViewPremiumActivity( hayFencesLiveStockPage ) );
        Premium hayFencesAfterPostcodeChange = premiumPage.getPremiumForRow( 1 );
        PremiumAssert.assertTotalPremiumChanged( hayFences, hayFencesAfterPostcodeChange );
        return hayFencesLiveStockPage;
    }
}
