package au.com.cgu.harvest.automation.scenario.farmmotor;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToCommentsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFirstVehiclePageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.VerifyCommentActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AddCommentActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.CommentsPage;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.VehicleDetailPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Add Comment as Internal Comment to Private Motor Vehicle and Delete the Vehicle to Check the Comment is Attcahed to Policy in Farm Motor Scenario" )
public class AddCommentToPMVAndDeleteSectionWithCommentScenario extends
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
            performActivity( new CreatePrivateMotorVehicleActivity(
                insuranceHistoryPage ) );

        ArrayList< String > sections = new ArrayList< String >();
        sections.add( "Policy" );
        sections.add( "AAA-999 HOLDEN BARINA" );
        HarvestPage page =
            performActivity( new AddCommentActivity( vehiclePage, sections, "First Comment",
                "Internal Comment", "Current Term" ) );
        sections.add( "Policy" );
        sections.add( "AAA-999 HOLDEN BARINA" );
        page =
            performActivity( new AddCommentActivity( vehiclePage, sections, "Second Comment",
                "Internal Comment", "Current Term" ) );

        CommentsPage commentsPage = performActivity( new NavigateToCommentsActivity( page ) );

        performActivity( new VerifyCommentActivity( commentsPage, 1, "Second Comment",
            "Internal Comment", "Current Term", "AAA-999 HOLDEN BARINA" ) );
        performActivity( new VerifyCommentActivity( commentsPage, 2, "First Comment",
            "Internal Comment", "Current Term", "Policy" ) );

        vehiclePage =
            performActivity( new NavigateToFirstVehiclePageActivity( commentsPage ) );
        VehicleSummaryPage vehicleSummaryPage =
            performActivity( new DeleteVehicleActivity< VehicleDetailPage >( vehiclePage ) );

        commentsPage = performActivity( new NavigateToCommentsActivity( vehiclePage ) );

        performActivity( new VerifyCommentActivity( commentsPage, 1, "Second Comment",
            "Internal Comment", "Current Term", "Policy" ) );

    }
}
