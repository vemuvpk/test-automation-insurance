package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyForQuoteWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToPolicyDetailPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStageAndStatusActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorQuoteActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SavePolicyActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Finish A Quote With Referrals Scenario" )
public class FinishAQuoteWithReferralScenario extends AbstractScenario
{
    @Test
    public void execute()
    {

        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchFarmMotorQuoteActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );
        policyDetailPage.getFarmMotorQuoteNumber();

        InsuranceHistoryPage insuranceHistoryPage =
            performActivity( new InsuranceHistoryActivity( policyDetailPage ) );

        PrivateMotorVehiclePage vehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );
        vehiclePage =
            performActivity( new EditPrivateMotorVehicleActivity( vehiclePage, 1 ) );
        vehiclePage.setObtainedProofOfClaimFreeYears( "No" );

        policyDetailPage = performActivity( new NavigateToPolicyDetailPageActivity( vehiclePage ) );

        policyDetailPage =
            performActivity( new AssertPolicyStageAndStatusActivity( policyDetailPage,
                "Farm Motor", "Quote", "Referral Required" ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyForQuoteWithReferralActivity( policyDetailPage ) );
        newBusinessPage =
            performActivity( new SavePolicyActivity( newBusinessPage ) );

    }

}
