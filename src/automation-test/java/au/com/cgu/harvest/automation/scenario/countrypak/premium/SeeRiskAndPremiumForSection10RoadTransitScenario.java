package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToRoadTransitPageActivity;
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
@Scenario( "See Risk and premium calculated for Section 10 - Road Transit scenario" )
public class SeeRiskAndPremiumForSection10RoadTransitScenario extends AbstractScenario
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

        NewBusinessPage newBusinessPage;

        roadTransitPage =
            verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( roadTransitPage );

        verifyPremiumDecreasesWhenRatingFactorChanges( roadTransitPage );

    }

    private void verifyPremiumDecreasesWhenRatingFactorChanges(
        RoadTransitPage roadTransitPage )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( roadTransitPage ) );
        Premium roadTransit = premiumPage.getPolicyPremium();
        roadTransitPage =
            performActivity( new NavigateToRoadTransitPageActivity( roadTransitPage ) );
        roadTransitPage.setSumInsured( "50000" );

        premiumPage = performActivity( new ViewPremiumActivity( roadTransitPage ) );
        Premium roadTransitAfterRatingFactorChange = premiumPage.getPolicyPremium();

        PremiumAssert.assertTotalPremiumChanged( roadTransit,
            roadTransitAfterRatingFactorChange );

        newBusinessPage =
            performActivity( new SuspendActivity( roadTransitPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( roadTransitPage ) );
        Premium farmMachineryAftereditingThePolicy = premiumPage.getPolicyPremium();

        PremiumAssert.assertTotalPremiumUnchanged( roadTransitAfterRatingFactorChange,
            farmMachineryAftereditingThePolicy );
    }

    private RoadTransitPage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy(
        RoadTransitPage roadTransitPage )
    {
        PolicyDetailPage policyDetailPage;

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( roadTransitPage ) );

        Premium farmMachinery = premiumPage.getPolicyPremium();

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( roadTransitPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        premiumPage =
            performActivity( new ViewPremiumActivity( roadTransitPage ) );
        Premium farmMachineryAfteredittingThePolicy = premiumPage.getPolicyPremium();
        PremiumAssert.assertTotalPremiumUnchanged( farmMachinery,
            farmMachineryAfteredittingThePolicy );
        return roadTransitPage;
    }
}
