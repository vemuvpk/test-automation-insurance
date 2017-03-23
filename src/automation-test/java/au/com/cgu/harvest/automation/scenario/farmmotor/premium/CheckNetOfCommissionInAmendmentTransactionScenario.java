package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithNoWindscreenExtensionActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Check Net of Commission  calculations in Amendment transactions scenario" )
public class CheckNetOfCommissionInAmendmentTransactionScenario
    extends
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
            performActivity( new CreatePrivateMotorVehicleWithNoWindscreenExtensionActivity(
                insuranceHistoryPage ) );

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        assertNetOfCommissionIsNegativeInAmendmentTransaction( vehiclePage, premiumPage );

    }

    private void assertNetOfCommissionIsNegativeInAmendmentTransaction(
        PrivateMotorVehiclePage vehiclePage, PremiumPage premiumPage )
    {
        PolicyDetailPage policyDetailPage;
        String amountInNB = premiumPage.getCommission();

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity( premiumPage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        PremiumAssert.assertNonZeroPolicyTotalPremium( premiumPage );
        premiumPage.setNetOfCommission( "true" );

        String amountInAmendment = premiumPage.getCommission();

        PremiumAssert.assertLessThan( "Commission", amountInAmendment, amountInNB );

        assertNetOfCommissionIsRightInAmendmentTransaction( vehiclePage, premiumPage,
            amountInAmendment );
    }

    private void assertNetOfCommissionIsRightInAmendmentTransaction(
        PrivateMotorVehiclePage vehiclePage, PremiumPage premiumPage, String amountInAmendment )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage;
        newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity( premiumPage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        PremiumAssert.assertNonZeroPolicyTotalPremium( premiumPage );
        premiumPage.setNetOfCommission( "false" );
        String amountInNextAmendment = premiumPage.getCommission();

        PremiumAssert.assertGreaterThan( "Commission", amountInNextAmendment, amountInAmendment );
    }

}
