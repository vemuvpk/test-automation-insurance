package au.com.cgu.harvest.automation.scenario.farmmotor.endorsements;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFirstVehiclePageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendBusinessActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.VehicleDetailPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Restore the inforce values for any deleted vehicle during Endorsement transaction for Farm Motor Scenario" )
public class AssertPremiumWhenVehicleDeletedInEndorsementTransactionScenario extends
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

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium totalPremiumForVehicle =
            premiumPage.getPremiumGrid().getFooterRow().getPolicyTotalPremium();

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( vehiclePage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( finishPage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        // Navigate to vehicle details page and change one of the rating factors to trigger change
        // in premium
        vehiclePage =
            performActivity( new NavigateToFirstVehiclePageActivity( policyDetailPage ) );
        vehiclePage.setWindScreenExtension( "false" );

        // Suspend, save and edit the policy
        newBusinessPage =
            performActivity( new SuspendBusinessActivity< HarvestPage >( vehiclePage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        // delete the vehicle and chk that the transaction premium refund is same as the actual
        // original premium
        vehiclePage =
            performActivity( new NavigateToFirstVehiclePageActivity( policyDetailPage ) );

        VehicleSummaryPage vehicleSummaryPage =
            performActivity( new DeleteVehicleActivity< VehicleDetailPage >(
                vehiclePage ) );

        premiumPage =
            performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium totalPremiumForVehicleAfterEdit =
            premiumPage.getPremiumGrid().getFooterRow().getPolicyTotalPremium();

        Assert.assertEquals(
            false,
            totalPremiumForVehicle.getTotalPremium()
                .add( totalPremiumForVehicleAfterEdit.getTotalPremium() )
                .equals( PremiumAssert.DECIMAL_ZERO ) );
    }

}
