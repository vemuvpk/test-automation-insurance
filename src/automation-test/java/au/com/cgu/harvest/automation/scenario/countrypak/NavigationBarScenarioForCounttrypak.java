package au.com.cgu.harvest.automation.scenario.countrypak;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigationBarClickNextActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigationBarClickPreviousActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Navigation Bar Scenario for Countrypak" )
public class NavigationBarScenarioForCounttrypak extends AbstractScenario
{
    @Test
    public void execute()
    {
        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchCountrypakActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        HarvestPage navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( policyDetailPage ) );
        assertEquals( "Insurance History", navBar.getNavigationBar().getPageHeading() );
        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Situation", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Domestic Buildings and Contents", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Farm Property", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Hay, Fencing, Livestock and Farm Trees", navBar.getNavigationBar()
            .getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Domestic Buildings and Contents", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Farm Property", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Farm Machinery and Working Dogs", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Theft", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Business Interruption", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Business Liability", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Machinery Breakdown", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Personal Income", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Road Transit", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Insured Details", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Interested Parties", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickNextActivity< HarvestPage >( navBar ) );
        assertEquals( "Finish", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Interested Parties", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Insured Details", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Road Transit", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Personal Income", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Machinery Breakdown", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Business Liability", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Business Interruption", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Theft", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Farm Machinery and Working Dogs", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Farm Property", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Domestic Buildings and Contents", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Hay, Fencing, Livestock and Farm Trees", navBar.getNavigationBar()
            .getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Farm Property", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Domestic Buildings and Contents", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Situation", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Insurance History", navBar.getNavigationBar().getPageHeading() );

        navBar =
            performActivity( new NavigationBarClickPreviousActivity< HarvestPage >( navBar ) );
        assertEquals( "Policy Details", navBar.getNavigationBar().getPageHeading() );
    }
}
