package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuredDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFirstVehiclePageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendBusinessActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SavePolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ViewRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.InsuredDetailsPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.ReadOnlyChecker;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Vehicle Value field should be read-only when the policy is opened in read-only mode scenario" )
public class AssertVehiclePageIsReadOnlyWhenPolicyOpenedInReadOnlyModeScenario extends
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
        vehiclePage =
            performActivity( new EditPrivateMotorVehicleActivity( vehiclePage, 1 ) );

        InsuredDetailsPage insuredDetailsPage =
            performActivity( new InsuredDetailsActivity( vehiclePage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendBusinessActivity< InsuredDetailsPage >(
                insuredDetailsPage ) );
        newBusinessPage =
            performActivity( new SavePolicyActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ViewRiskDetailsActivity( newBusinessPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), policyDetailPage );

        vehiclePage =
            performActivity( new NavigateToFirstVehiclePageActivity( insuranceHistoryPage ) );
        ReadOnlyChecker.checkReadOnly( getWebDriver(), vehiclePage );

    }

}
