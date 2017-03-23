package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AssertForIntermediaryFeeForThisTermInTotalPremiumActivity;
import au.com.cgu.harvest.automation.activity.AssertForIntermediaryFeeForThisTransactionInTotalPremiumActivity;
import au.com.cgu.harvest.automation.activity.SaveTransactionAsNewPolicyWithCreditCardAndSetIntermediaryFeesActivity;
import au.com.cgu.harvest.automation.activity.SaveTransactionAsNewPolicyWithDirectDebitAndSetIntermediaryFeesActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.TotalPremiumPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "To Check Instalment Billing details on Premium Page for Countrypak scenario" )
public class ToCheckInstalmentBillingOnPremiumPageForCountrypakScenario extends AbstractScenario
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

        RoadTransitPage roadTransit =
            performActivity( new AddRoadTransitActivity( situationLevelSectionPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( roadTransit ) );
        finishPage =
            performActivity( new SaveTransactionAsNewPolicyWithCreditCardAndSetIntermediaryFeesActivity(
                finishPage ) );

        TotalPremiumPage totalPremiumPage =
            performActivity( new AssertForIntermediaryFeeForThisTransactionInTotalPremiumActivity(
                roadTransit, "$150.00", "$15.00", "$165.00" ) );

        totalPremiumPage =
            performActivity( new AssertForIntermediaryFeeForThisTermInTotalPremiumActivity(
                roadTransit, "$150.00", "$15.00", "$165.00" ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( totalPremiumPage ) );
        finishPage =
            performActivity( new SaveTransactionAsNewPolicyWithDirectDebitAndSetIntermediaryFeesActivity(
                finishPage ) );

        totalPremiumPage =
            performActivity( new AssertForIntermediaryFeeForThisTransactionInTotalPremiumActivity(
                roadTransit, "$150.00", "$15.00", "$165.00" ) );

        totalPremiumPage =
            performActivity( new AssertForIntermediaryFeeForThisTermInTotalPremiumActivity(
                roadTransit, "$150.00", "$15.00", "$165.00" ) );

    }
}
