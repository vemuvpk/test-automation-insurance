package au.com.cgu.harvest.automation.scenario.farmmotor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigationBarClickNextActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigationBarClickPreviousActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Navigation Bar Scenario for FarmMotor" )
public class NavigationBarScenarioForFarmMotor extends AbstractScenario
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

        HarvestPage harvestPage =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( policyDetailPage ) );
        assertEquals( "Insurance History", harvestPage.getNavigationBar().getPageHeading() );
        harvestPage =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( harvestPage ) );
        assertEquals( "Vehicle Details", harvestPage.getNavigationBar().getPageHeading() );

        harvestPage =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( harvestPage ) );
        assertEquals( "Insured Details", harvestPage.getNavigationBar().getPageHeading() );

        harvestPage =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( harvestPage ) );
        assertEquals( "Finish", harvestPage.getNavigationBar().getPageHeading() );

        harvestPage =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( harvestPage ) );
        assertEquals( "Insured Details", harvestPage.getNavigationBar().getPageHeading() );

        harvestPage =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( harvestPage ) );
        assertEquals( "Vehicle Details", harvestPage.getNavigationBar().getPageHeading() );

        harvestPage =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( harvestPage ) );
        assertEquals( "Insurance History", harvestPage.getNavigationBar().getPageHeading() );

        harvestPage =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( harvestPage ) );
        assertEquals( "Policy Details", harvestPage.getNavigationBar().getPageHeading() );
    }
}
