package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AbortActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyPolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.RenewRiskDetailsActivity;
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
@Scenario( "Check Premium Calculations in Amendment transactions for Farm Motor scenario" )
public class CheckPremiumCalculationsInAmendmentTransactionScenario
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
            performActivity( new CreatePrivateMotorVehicleActivity(
                insuranceHistoryPage ) );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( vehiclePage ) );

        String amountInNB = premiumPage.getCommission();

        // Extend premium term
        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity( premiumPage ) );

        premiumPage = assertPremiumAmountForChangeInTerm( vehiclePage, newBusinessPage );

        String premiumInAmendment = assertRefundInCommission( premiumPage, amountInNB );

        // Change Net of Commission
        premiumPage = assertRefundOfNetOfCommission( vehiclePage, premiumPage, premiumInAmendment );

        // Stamp duty exemption
        assertStampDutyRefund( vehiclePage, premiumPage );

    }

    private String assertRefundInCommission( PremiumPage premiumPage, String amountInNB )
    {
        premiumPage.setNetOfCommission( "true" );
        String amountInAmendment = premiumPage.getCommission();
        String premiumInAmendment =
            premiumPage.getPolicyTotalPremiumFromFooter();
        PremiumAssert.assertLessThan( "Commission", amountInAmendment, amountInNB );
        return premiumInAmendment;
    }

    private PremiumPage assertPremiumAmountForChangeInTerm( PrivateMotorVehiclePage vehiclePage,
        NewBusinessPage newBusinessPage )
    {
        PolicyDetailPage policyDetailPage;
        PremiumPage premiumPage;
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new ModifyPolicyActivity( newBusinessPage ) );
        newBusinessPage.setEndDate( new LocalDate().plusYears( 1 ).plusMonths( 1 )
            .toString( "dd/MM/yyyy" ) );

        policyDetailPage =
            performActivity( new RenewRiskDetailsActivity( newBusinessPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        PremiumAssert.assertNonZeroPolicyTotalPremium( premiumPage );
        return premiumPage;
    }

    private PremiumPage assertRefundOfNetOfCommission( PrivateMotorVehiclePage vehiclePage,
        PremiumPage premiumPage, String premiumInAmendment )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage;
        newBusinessPage =
            performActivity( new AbortActivity( premiumPage ) );
        newBusinessPage.setEndDate( new LocalDate().plusYears( 1 ).toString( "dd/MM/yyyy" ) );
        policyDetailPage =
            performActivity( new RenewRiskDetailsActivity( newBusinessPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        PremiumAssert.assertNonZeroPolicyTotalPremium( premiumPage );
        premiumPage.setNetOfCommission( "true" );
        String newPremiumInAmendment =
            premiumPage.getPolicyTotalPremiumFromFooter();
        PremiumAssert.assertLessThan( "Policy Total Premium", newPremiumInAmendment,
            premiumInAmendment );
        return premiumPage;
    }

    private void assertStampDutyRefund( PrivateMotorVehiclePage vehiclePage, PremiumPage premiumPage )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage;
        newBusinessPage =
            performActivity( new AbortActivity( premiumPage ) );
        newBusinessPage.setEndDate( new LocalDate().plusYears( 1 ).toString( "dd/MM/yyyy" ) );
        policyDetailPage =
            performActivity( new RenewRiskDetailsActivity( newBusinessPage ) );

        policyDetailPage.setExemptFromStampDuty( "true" );
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        PremiumAssert.assertZeroStampDuty( premiumPage, 1 );
        PremiumAssert.assertNonZeroPolicyTotalPremium( premiumPage );
    }
}
