package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertForNewQuestionInInsuranceHistoryPage;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertMandatoryErrorForDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndAcceptActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Confirm criminal convictions and insurance history When New Vehicle is added to the policy" )
public class ConfirmCriminalConvictionsAndInsuranceHistoryForNewVehicleScenario extends
    AbstractScenario
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

        PrivateMotorVehiclePage vehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( vehiclePage ) );

        newBusinessPage =
            performActivity( new SaveAndAcceptActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        vehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );

        policyDetailPage =
            performActivity( new AssertMandatoryErrorForDutyOfDisclosureActivity( vehiclePage ) );

        insuranceHistoryPage =
            performActivity( new AssertForNewQuestionInInsuranceHistoryPage(
                policyDetailPage )
            );

    }

}
