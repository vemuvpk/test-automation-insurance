package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.FinishPolicyAsRenewalInvitationActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ClosePolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.RenewAndRenewRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndAcceptActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Zero Transaction Premium When a Covernote or Renewal is closed through Sunrise Close Action Scenario" )
public class AssertZeroTransactionPremiumForCloseFarmmotorPolicyScenario extends AbstractScenario
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
        AgriculturalVehiclePage agriculturalVehicle =
            performActivity( new CreateAgriculturalVehicleActivity( vehiclePage ) );

        policyDetailPage = finishAndClosePolicy( insuranceHistoryPage );
        assertNonZeroPremiumInTransactionPremiumPage( policyDetailPage );
        assertZeroPremiumWhenClosedAsRenewalInvitation( policyDetailPage );

    }

    private void assertZeroPremiumWhenClosedAsRenewalInvitation( PolicyDetailPage policyDetailPage )
    {
        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        NewBusinessPage newBusinessPage = finishPage.finish();

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new RenewAndRenewRiskDetailsActivity( newBusinessPage ) );

        newBusinessPage =
            performActivity( new FinishPolicyAsRenewalInvitationActivity( policyDetailPage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ClosePolicyActivity( newBusinessPage ) );
        assertNonZeroPremiumInTransactionPremiumPage( policyDetailPage );
    }

    private void assertZeroPremiumInTransactionPremiumPage( PolicyDetailPage policyDetailPage )
    {
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( policyDetailPage ) );
        PremiumAssert.assertZeroPolicyTotalPremium( premiumPage );
    }

    private void assertNonZeroPremiumInTransactionPremiumPage( PolicyDetailPage policyDetailPage )
    {
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( policyDetailPage ) );
        PremiumAssert.assertNonZeroPolicyTotalPremium( premiumPage );
    }

    private PolicyDetailPage finishAndClosePolicy(
        InsuranceHistoryPage insuranceHistoryPage )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity(
                insuranceHistoryPage ) );

        newBusinessPage =
            performActivity( new SaveAndAcceptActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ClosePolicyActivity( newBusinessPage ) );
        return policyDetailPage;
    }
}
