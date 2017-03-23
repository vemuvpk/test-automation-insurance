package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationWithNoAnnualTurnoverActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertPremiumsInRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForQLDStateActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToRoadTransitPageActivity;
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
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Premium Changes in Section 10 When Postcode is Changed for a Situation Scenario" )
public class AssertPremiumChangeWhenPostcodeChangedForSection10Scenario extends AbstractScenario
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
            performActivity( new CreateASituationForQLDStateActivity(
                insuranceHistoryPage ) );

        RoadTransitPage roadTransitpage =
            performActivity( new AddRoadTransitActivity( situationLevelSectionPage ) );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( roadTransitpage ) );

        verifyPremiumChangesWhenPostCodeChanges( premiumPage );

        roadTransitpage =
            verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( roadTransitpage );

        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 1 ) );

        situationLevelSectionPage =
            performActivity( new AddASituationWithNoAnnualTurnoverActivity(
                insuranceHistoryPage ) );

        verifyNilPremiumForNilAnnualTurnOver( roadTransitpage );

    }

    private void verifyNilPremiumForNilAnnualTurnOver( RoadTransitPage roadTransitpage )
    {
        SituationDetailPage situationLevelSectionPage;
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( roadTransitpage ) );

        roadTransitpage =
            performActivity( new NavigateToRoadTransitPageActivity( situationLevelSectionPage ) );
        roadTransitpage =
            performActivity( new AssertPremiumsInRoadTransitActivity( roadTransitpage, "" ) );
    }

    private RoadTransitPage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy(
        RoadTransitPage roadTransitpage )
    {
        PolicyDetailPage policyDetailPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( roadTransitpage ) );

        Premium roadTransit = premiumPage.getPremiumForRow( 1 );
        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( roadTransitpage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( roadTransitpage ) );

        Premium roadTransitAfterEditingThePolicy = premiumPage.getPremiumForRow( 1 );
        PremiumAssert.assertTotalPremiumUnchanged( roadTransit, roadTransitAfterEditingThePolicy );
        return roadTransitpage;
    }

    private void verifyPremiumChangesWhenPostCodeChanges( PremiumPage premiumPage )
    {
        RoadTransitPage roadTransitPage;
        SituationDetailPage situationLevelSectionPage;

        Premium roadTransit = premiumPage.getPremiumForRow( 1 );
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( premiumPage ) );
        situationLevelSectionPage.setSuburbStatePostcode( "Orange", "NSW", "2800" );

        premiumPage =
            performActivity( new ViewPremiumActivity( situationLevelSectionPage ) );
        Premium roadTransitAfterPostcodeChange = premiumPage.getPremiumForRow( 1 );
        PremiumAssert.assertTotalPremiumChanged( roadTransit, roadTransitAfterPostcodeChange );
    }
}
