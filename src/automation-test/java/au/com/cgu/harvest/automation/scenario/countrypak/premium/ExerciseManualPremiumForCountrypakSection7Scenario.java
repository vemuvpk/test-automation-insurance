package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessLiabilityWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ChangeRatingFactorInBusinessLiabilityPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForRaedownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.EditManualPremiumValuesInBusinessLiabilityPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessLiabilityPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Manual Premium For Countrypak Section 7 - Business Liability Scenario" )
public class ExerciseManualPremiumForCountrypakSection7Scenario extends AbstractScenario
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

        BusinessLiabilityPage businessLiabilityPage =
            performActivity( new AddBusinessLiabilityWithManualPremiumActivity(
                situationLevelSectionPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( businessLiabilityPage ) );

        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        businessLiabilityPage =
            performActivity( new NavigateToBusinessLiabilityPageActivity( policyDetailPage ) );

        ManuallyRatedBusinessLiabilityPage.assertPremiumValues( businessLiabilityPage );

        businessLiabilityPage =
            performActivity( new EditManualPremiumValuesInBusinessLiabilityPageActivity(
                businessLiabilityPage ) );

        newBusinessPage =
            performActivity( new SuspendActivity( businessLiabilityPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        businessLiabilityPage =
            performActivity( new NavigateToBusinessLiabilityPageActivity( policyDetailPage ) );

        ManuallyRatedBusinessLiabilityPage.assertNewPremiumValues( businessLiabilityPage );

        businessLiabilityPage =
            performActivity( new ChangeRatingFactorInBusinessLiabilityPageActivity(
                businessLiabilityPage ) );
    }
}
