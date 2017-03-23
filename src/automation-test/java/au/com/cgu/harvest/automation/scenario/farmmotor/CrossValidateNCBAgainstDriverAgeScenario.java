package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePageXpath;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "CFMC-204" )
@Scenario( "Cross Validate NCB Rating Against Driver Age" )
public class CrossValidateNCBAgainstDriverAgeScenario extends AbstractScenario
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
        checkNCBAgainstDriver( vehiclePage );

        vehiclePage.setGarageSuburbStatePostcode( "Ballarat", "VIC", "3350" );
        checkNCBAgainstDriver( vehiclePage );

        vehiclePage.setGarageSuburbStatePostcode( "Belconnen", "ACT", "2617" );
        checkNCBAgainstDriver( vehiclePage );

    }

    private void checkNCBAgainstDriver( PrivateMotorVehiclePage vehiclePage )
    {
        vehiclePage =
            performActivity( new EditPrivateMotorVehicleActivity( vehiclePage, 1 ) );
        vehiclePage.setAtFaultClaimFreeYears( "4 years" );
        vehiclePage.getDriverAtRow( 1 ).setDateOfBirth(
            new LocalDate().minusYears( 16 ).toString( "dd/MM/yyyy" ) );

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By
            .xpath( PrivateMotorVehiclePageXpath.DRIVER_DATE_OF_BIRTH ), "E088" ) );

        vehiclePage.setAtFaultClaimFreeYears( ">= 5 years (Protected)" );
        wait.until( Rule.isDisplayed( By
            .xpath( PrivateMotorVehiclePageXpath.DRIVER_DATE_OF_BIRTH ), "E088" ) );

        vehiclePage.setAtFaultClaimFreeYears( ">= 5 years" );
        wait.until( Rule.isDisplayed( By
            .xpath( PrivateMotorVehiclePageXpath.DRIVER_DATE_OF_BIRTH ), "E088" ) );
    }
}
