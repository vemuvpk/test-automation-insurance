package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessInterruptionWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ChangeRatingFactorInBusinessInterruptionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForRaedownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.EditManualPremiumValuesInBusinessInterruptionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessInterruptionPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Manual Premium For Countrypak Section 6 - Business Interruption Scenario" )
public class ExerciseManualPremiumForCountrypakSection6Scenario extends AbstractScenario
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

        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new AddBusinessInterruptionWithManualPremiumActivity(
                situationLevelSectionPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( businessInterruptionPage ) );

        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        businessInterruptionPage =
            performActivity( new NavigateToBusinessInterruptionPageActivity( policyDetailPage ) );

        ManuallyRatedBusinessInterruptionPage.assertPremiumValues( businessInterruptionPage );
        businessInterruptionPage =
            performActivity( new EditManualPremiumValuesInBusinessInterruptionPageActivity(
                businessInterruptionPage ) );

        newBusinessPage =
            performActivity( new SuspendActivity( businessInterruptionPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        businessInterruptionPage =
            performActivity( new NavigateToBusinessInterruptionPageActivity( policyDetailPage ) );
        ManuallyRatedBusinessInterruptionPage.assertNewPremiumValues( businessInterruptionPage );
        businessInterruptionPage =
            performActivity( new ChangeRatingFactorInBusinessInterruptionPageActivity(
                businessInterruptionPage ) );
    }
}
