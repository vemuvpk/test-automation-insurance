package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ModifyStartDateToCheckReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyStartDateAndEndDateToCheckReferralActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Endorsement - validate Policy data on Policy Header for Farm Motor Scenario" )
public class EndorsementValidatePolicyDataOnHeaderScenario extends
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
        vehiclePage.setVehicleIsFinanced( "true" );
        vehiclePage.addInterestedParty().setWellKnownFinanceTypeWellKnownInterestedParty();

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity(
                vehiclePage ) );

        policyDetailPage =
            performActivity( new ModifyStartDateToCheckReferralActivity( newBusinessPage,
                "18/05/2011", "R057" ) );
        newBusinessPage =
            performActivity( new SuspendActivity( policyDetailPage ) );
        policyDetailPage =
            performActivity( new ModifyStartDateToCheckReferralActivity( newBusinessPage,
                new LocalDate().plusMonths( 6 ).plusDays( 2 ).toString( "dd/MM/yyyy" ), "R058" ) );
        newBusinessPage =
            performActivity( new SuspendActivity( policyDetailPage ) );
        policyDetailPage =
            performActivity( new ModifyStartDateAndEndDateToCheckReferralActivity(
                newBusinessPage,
                "18/08/2011", "18/09/2011", "R059" ) );
        newBusinessPage =
            performActivity( new SuspendActivity( policyDetailPage ) );
        policyDetailPage =
            performActivity( new ModifyStartDateAndEndDateToCheckReferralActivity(
                newBusinessPage,
                "18/05/2020", "18/09/2021", "R060" ) );
    }
}
