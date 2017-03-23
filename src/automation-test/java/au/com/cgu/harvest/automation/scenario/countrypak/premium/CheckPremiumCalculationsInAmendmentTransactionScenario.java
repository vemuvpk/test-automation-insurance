package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AbortActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyPolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.RenewRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Check Premium Calculations in Amendment transactions For Countrypak scenario" )
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
            performActivity( new LaunchCountrypakActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        CountrypakInsuranceHistoryPage insuranceHistoryPage =
            performActivity( new CountrypakInsuranceHistoryActivity( policyDetailPage ) );

        SituationDetailPage situationLevelSectionPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                insuranceHistoryPage ) );
        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( farmPropertyPage ) );

        String amountInNB = premiumPage.getCommission();

        // Extend premium term
        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity( premiumPage ) );

        premiumPage = assertPremiumAmountForChangeInTerm( farmPropertyPage, newBusinessPage );

        String premiumInAmendment = assertRefundInCommission( premiumPage, amountInNB );

        // Change Net of Commission
        premiumPage =
            assertRefundOfNetOfCommission( farmPropertyPage, premiumPage, premiumInAmendment );

        // Stamp duty exemption
        assertStampDutyRefund( farmPropertyPage, premiumPage );

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

    private PremiumPage assertPremiumAmountForChangeInTerm( FarmPropertyPage farmPropertyPage,
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

        premiumPage = performActivity( new ViewPremiumActivity( farmPropertyPage ) );

        PremiumAssert.assertNonZeroPolicyTotalPremium( premiumPage );
        return premiumPage;
    }

    private PremiumPage assertRefundOfNetOfCommission( FarmPropertyPage farmPropertyPage,
        PremiumPage premiumPage, String premiumInAmendment )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage;
        newBusinessPage =
            performActivity( new AbortActivity( premiumPage ) );
        newBusinessPage.setEndDate( new LocalDate().plusYears( 1 ).toString( "dd/MM/yyyy" ) );
        policyDetailPage =
            performActivity( new RenewRiskDetailsActivity( newBusinessPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( farmPropertyPage ) );
        PremiumAssert.assertNonZeroPolicyTotalPremium( premiumPage );
        premiumPage.setNetOfCommission( "true" );
        String newPremiumInAmendment =
            premiumPage.getPolicyTotalPremiumFromFooter();
        PremiumAssert.assertLessThan( "Policy Total Premium", newPremiumInAmendment,
            premiumInAmendment );
        return premiumPage;
    }

    private void assertStampDutyRefund( FarmPropertyPage farmPropertyPage, PremiumPage premiumPage )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage;
        newBusinessPage =
            performActivity( new AbortActivity( premiumPage ) );
        newBusinessPage.setEndDate( new LocalDate().plusYears( 1 ).toString( "dd/MM/yyyy" ) );
        policyDetailPage =
            performActivity( new RenewRiskDetailsActivity( newBusinessPage ) );

        policyDetailPage.setExemptFromStampDuty( "true" );
        premiumPage = performActivity( new ViewPremiumActivity( farmPropertyPage ) );
        PremiumAssert.assertZeroStampDuty( premiumPage, 1 );
        PremiumAssert.assertNonZeroPolicyTotalPremium( premiumPage );
    }
}
