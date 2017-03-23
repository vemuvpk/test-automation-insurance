package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertCertificateOfCurrencyOnFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Certificate of Currency is present on the Finish page" )
public class AssertCertificateOfCurrencyOnFinishPageScenario extends
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
        vehiclePage.addInterestedParty().selectWellKnownFinanceTypeAndOtherInterestedParty();

        AgriculturalVehiclePage agriculturalVehiclePage =
            performActivity( new CreateAgriculturalVehicleActivity( vehiclePage ) );
        agriculturalVehiclePage =
            performActivity( new EditAgriculturalVehicleActivity( vehiclePage, 2 ) );
        agriculturalVehiclePage.setVehicleIsFinanced( "true" );
        agriculturalVehiclePage.addInterestedParty()
            .selectOtherFinanceTypeAndOtherInterestedParty();

        NewBusinessPage newBusinessPage =
            performActivity( new AssertCertificateOfCurrencyOnFinishPageActivity(
                vehiclePage ) );

    }

}
