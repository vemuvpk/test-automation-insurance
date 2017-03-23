package au.com.cgu.harvest.automation.scenario.farmmotor;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.VerifyCommentActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AddCommentActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.NavigateToCommentsPageActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.CommentsPage;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Add Comment at Policy and risk level to Private Motor Vehicle Scenario" )
public class AddCommentToPMVScenario extends
    AbstractScenario
{
    // public AddCommentToPMVScenario( String browser,
    // String browserVersion )
    // {
    // super( browser, browserVersion );
    // }

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

        sections.add( "AAA-999 HOLDEN BARINA" );
        sections.add( "Policy" );
        page =
            performActivity( new AddCommentActivity( vehiclePage, sections, "Risk Level Comment",
                "Internal Comment", "Current Term" ) );
        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( page ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        CommentsPage commentsPage =
            performActivity( new NavigateToCommentsPageActivity( policyDetailPage ) );

        commentsPage =
            performActivity( new VerifyCommentActivity( commentsPage, 1, "Risk Level Comment",
                "Internal Comment", "Current Term", "AAA-999 HOLDEN BARINA" ) );
        commentsPage =
            performActivity( new VerifyCommentActivity( commentsPage, 2, "First Comment",
                "Internal Comment", "Current Term", "Policy" ) );

    }
}
