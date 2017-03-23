package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AddCancellationReasonAndFinishThePolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStateFromSunriseActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.TerminatePolicyActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Policy Termination of New Business Auto Closed Accepted for Farm Motor Scenario" )
public class PolicyTerminationOfNBAutoClosedAcceptedScenario extends
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

        AgriculturalVehiclePage agriculturalVehiclePage =
            performActivity( new CreateAgriculturalVehicleActivity( insuranceHistoryPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( agriculturalVehiclePage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( finishPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "New Business Auto Closed Complete" ) );
        newBusinessPage = performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "New Business Auto Closed Accepted" ) );

        PremiumPage premiumPage =
            performActivity( new TerminatePolicyActivity( newBusinessPage ) );

        newBusinessPage =
            performActivity( new AddCancellationReasonAndFinishThePolicyActivity( premiumPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Cancellation Auto Closed Complete" ) );

        newBusinessPage = performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateFromSunriseActivity( newBusinessPage,
                "Cancellation Auto Closed Accepted" ) );

    }
}
