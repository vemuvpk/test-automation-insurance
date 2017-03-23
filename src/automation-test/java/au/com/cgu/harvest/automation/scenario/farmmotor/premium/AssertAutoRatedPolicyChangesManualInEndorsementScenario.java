package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.JavascriptExecutor;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertErrorRulesForManualPMVActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertReferralForManualPMVActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFirstVehiclePageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Auto Rated Policy Chnages to Manually rated If it cannot be rated in Endorsement for Farm Motor Scenario" )
public class AssertAutoRatedPolicyChangesManualInEndorsementScenario extends AbstractScenario
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

        PrivateMotorVehiclePage privateVehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity( policyDetailPage ) );

        doAnEndorsementTransactionActivity( privateVehiclePage );
        changePostCodeToRaedown( privateVehiclePage );

        PrivateMotorVehiclePage newPrivateVehiclePage =
            privateVehiclePage.getNavigationTree().navigateToPrivateMotorVehicle( 1 );

        newPrivateVehiclePage =
            performActivity( new AssertReferralForManualPMVActivity( newPrivateVehiclePage ) );
    }

    private void doAnEndorsementTransactionActivity( HarvestPage page )
    {
        PolicyDetailPage policyDetailPage;
        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( page ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( finishPage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
    }

    private PrivateMotorVehiclePage changePostCodeToRaedown(
        PrivateMotorVehiclePage privateVehiclePage )
    {
        privateVehiclePage =
            performActivity( new NavigateToFirstVehiclePageActivity( privateVehiclePage ) );

        privateVehiclePage.setGarageSuburbStatePostcode( "Raedown", "NSW", "9999" );
        privateVehiclePage =
            performActivity( new AssertErrorRulesForManualPMVActivity( privateVehiclePage ) );

        privateVehiclePage.setStandardExcess( "$500" );
        privateVehiclePage.setTransactionBasePremium( "999.99" );

        ( ( JavascriptExecutor ) getWebDriver() )
            .executeScript( "window.confirm = function(msg){return true;};" );
        privateVehiclePage.setTransactionWindscreenPremium( "45.45" );

        return privateVehiclePage;
    }

}
