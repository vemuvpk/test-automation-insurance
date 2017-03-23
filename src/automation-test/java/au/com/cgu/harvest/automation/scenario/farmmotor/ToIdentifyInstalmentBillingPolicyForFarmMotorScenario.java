package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AssertPolicyStageAndStatusInHeaderActivity;
import au.com.cgu.harvest.automation.activity.SetSaveTransactionAsAndPaymentMethodActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "To Identify Instalment Billing Policy or Paid By in policy header for FarmMotor scenario" )
public class ToIdentifyInstalmentBillingPolicyForFarmMotorScenario extends AbstractScenario
{
    @Test
    public void execute()
    {
        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchFarmMotorActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        InsuranceHistoryPage insuranceHistoryPage =
            performActivity( new InsuranceHistoryActivity( policyDetailPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( insuranceHistoryPage ) );

        finishPage =
            performActivity( new SetSaveTransactionAsAndPaymentMethodActivity( finishPage,
                "Policy", "Monthly Direct Debit" ) );
        PolicyDetailPage policyHeader =
            performActivity( new AssertPolicyStageAndStatusInHeaderActivity( finishPage,
                "New Business", "Draft", "Monthly Direct Debit" ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        finishPage =
            performActivity( new SetSaveTransactionAsAndPaymentMethodActivity( finishPage,
                "Policy", "Monthly Credit Card" ) );
        policyHeader =
            performActivity( new AssertPolicyStageAndStatusInHeaderActivity( finishPage,
                "New Business", "Draft", "Monthly Credit Card" ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        finishPage =
            performActivity( new SetSaveTransactionAsAndPaymentMethodActivity( finishPage,
                "Policy", "Intermediary Statement" ) );
        policyHeader =
            performActivity( new AssertPolicyStageAndStatusInHeaderActivity( finishPage,
                "New Business", "Draft", "Intermediary Statement" ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        finishPage =
            performActivity( new SetSaveTransactionAsAndPaymentMethodActivity( finishPage,
                "Policy", "" ) );
        policyHeader =
            performActivity( new AssertPolicyStageAndStatusInHeaderActivity( finishPage,
                "New Business", "Draft", "" ) );

    }
}
