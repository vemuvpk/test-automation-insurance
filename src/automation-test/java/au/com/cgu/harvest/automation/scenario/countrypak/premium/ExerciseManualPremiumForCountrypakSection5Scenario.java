package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ChangeRatingFactorInHayFencesPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForRaedownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.EditManualPremiumValuesInHayFencesPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToHayFencesPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Manual Premium For Countrypak Section 5 - Hay, Fences and Livestock Scenario" )
public class ExerciseManualPremiumForCountrypakSection5Scenario extends AbstractScenario
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

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceWithManualPremiumActivity( situationLevelSectionPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( hayFencesLiveStockPage ) );

        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        hayFencesLiveStockPage =
            performActivity( new NavigateToHayFencesPageActivity( policyDetailPage ) );
        ManuallyRatedHayFencesPage.assertPremiumValues( hayFencesLiveStockPage );
        hayFencesLiveStockPage =
            performActivity( new EditManualPremiumValuesInHayFencesPageActivity(
                hayFencesLiveStockPage ) );

        newBusinessPage =
            performActivity( new SuspendActivity( hayFencesLiveStockPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        hayFencesLiveStockPage =
            performActivity( new NavigateToHayFencesPageActivity( policyDetailPage ) );
        ManuallyRatedHayFencesPage.assertNewPremiumValues( hayFencesLiveStockPage );
        hayFencesLiveStockPage =
            performActivity( new ChangeRatingFactorInHayFencesPageActivity( hayFencesLiveStockPage )
            );

    }
}
