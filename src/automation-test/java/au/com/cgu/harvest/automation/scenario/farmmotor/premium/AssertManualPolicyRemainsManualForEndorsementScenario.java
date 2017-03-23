package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.JavascriptExecutor;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertReferralForManualPMVActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithReferralActivity;
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
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Manually Rated Policy Remains Manually rated in Endorsement for Farm Motor Scenario" )
public class AssertManualPolicyRemainsManualForEndorsementScenario extends AbstractScenario
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
            performActivity( new CreatePrivateMotorVehicleWithManualPremiumActivity(
                policyDetailPage ) );

        doAnEndorsementTransactionActivity( privateVehiclePage );
        changePostCode( privateVehiclePage );

        PrivateMotorVehiclePage newPrivateVehiclePage =
            privateVehiclePage.getNavigationTree().navigateToPrivateMotorVehicle( 1 );

        privateVehiclePage =
            performActivity( new AssertReferralForManualPMVActivity( privateVehiclePage ) );
    }

    private void doAnEndorsementTransactionActivity( HarvestPage page )
    {
        PolicyDetailPage policyDetailPage;
        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( page ) );

        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyAsNewPolicyWithReferralActivity( finishPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
    }

    private PrivateMotorVehiclePage changePostCode(
        PrivateMotorVehiclePage privateVehiclePage )
    {

        privateVehiclePage =
            performActivity( new NavigateToFirstVehiclePageActivity( privateVehiclePage ) );

        privateVehiclePage.setGarageSuburbStatePostcode( "Orange", "NSW", "2800" );

        privateVehiclePage.setStandardExcess( "$500" );
        privateVehiclePage.setTransactionBasePremium( "999.99" );

        ( ( JavascriptExecutor ) getWebDriver() )
            .executeScript( "window.confirm = function(msg){return true;};" );
        privateVehiclePage.setTransactionWindscreenPremium( "45.45" );

        return privateVehiclePage;
    }

}
