package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AssertForIntermediaryFeeForThisTermInTotalPremiumActivity;
import au.com.cgu.harvest.automation.activity.AssertForIntermediaryFeeForThisTransactionInTotalPremiumActivity;
import au.com.cgu.harvest.automation.activity.SaveTransactionAsNewPolicyWithCreditCardAndSetIntermediaryFeesActivity;
import au.com.cgu.harvest.automation.activity.SaveTransactionAsNewPolicyWithDirectDebitAndSetIntermediaryFeesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.TotalPremiumPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "To Check Instalment Billing details on Premium Page for FarmMotor scenario" )
public class ToCheckInstalmentBillingOnPremiumPageForFarmMotorScenario extends AbstractScenario
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

        AgriculturalVehiclePage vehiclePage =
            performActivity( new CreateAgriculturalVehicleActivity(
                insuranceHistoryPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( vehiclePage ) );
        finishPage =
            performActivity( new SaveTransactionAsNewPolicyWithCreditCardAndSetIntermediaryFeesActivity(
                finishPage ) );

        TotalPremiumPage totalPremiumPage =
            performActivity( new AssertForIntermediaryFeeForThisTransactionInTotalPremiumActivity(
                vehiclePage, "$150.00", "$15.00", "$165.00" ) );

        totalPremiumPage =
            performActivity( new AssertForIntermediaryFeeForThisTermInTotalPremiumActivity(
                vehiclePage, "$150.00", "$15.00", "$165.00" ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( totalPremiumPage ) );
        finishPage =
            performActivity( new SaveTransactionAsNewPolicyWithDirectDebitAndSetIntermediaryFeesActivity(
                finishPage ) );

        totalPremiumPage =
            performActivity( new AssertForIntermediaryFeeForThisTransactionInTotalPremiumActivity(
                vehiclePage, "$150.00", "$15.00", "$165.00" ) );

        totalPremiumPage =
            performActivity( new AssertForIntermediaryFeeForThisTermInTotalPremiumActivity(
                vehiclePage, "$150.00", "$15.00", "$165.00" ) );

    }
}
