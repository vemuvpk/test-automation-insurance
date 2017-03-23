package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverridePremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ResetPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.NumberConverter;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Perform an override of the premium by more than a Dollar to Check Referral Message for an auto-rated vehicle" )
public class OverridePremiumToCheckReferralForAutoRatedVehicleScenario extends AbstractScenario
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

        // VERIFY (6) - Apply an override to get Referral Message
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        verifyOverrideAppliesAndCheckForReferral( premiumPage, "$2.18" );
        Premium autoRatedVehiclePremiumAfterOverride = premiumPage.getPremiumForRow( 1 );

        // Suspend, Save and edit the policy
        NewBusinessPage newBusinessPage = performActivity( new SuspendActivity( premiumPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        // VERIFY (6.1) - Verify no changes after resuming from suspend
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        Premium autoRatedVehiclePremiumAfterSuspend = premiumPage.getPremiumForRow( 1 );
        premiumPage.assertReferralRules( "R035" );

        PremiumAssert.assertTotalPremiumUnchanged( autoRatedVehiclePremiumAfterOverride,
            autoRatedVehiclePremiumAfterSuspend );

        // VERIFY (6.2) - Reset
        premiumPage = performActivity( new ResetPremiumActivity( premiumPage ) );

        // VERIFY (7) - Apply an override to get negative override and Referral Message and check
        // calculation and Referral is correct

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        verifyOverrideAppliesAndCheckForReferral( premiumPage, "-$2.18" );
        Premium autoRatedVehiclePremiumAfterNegativeOverride = premiumPage.getPremiumForRow( 1 );

        // Suspend, Save and edit the policy
        newBusinessPage = performActivity( new SuspendActivity( premiumPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        // VERIFY (7.1) - Verify no changes after resuming from suspend
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        Premium autoRatedVehiclePremiumAfterNegativeOverrideSuspend =
            premiumPage.getPremiumForRow( 1 );
        premiumPage.assertReferralRules( "R035" );

        PremiumAssert.assertTotalPremiumUnchanged( autoRatedVehiclePremiumAfterNegativeOverride,
            autoRatedVehiclePremiumAfterNegativeOverrideSuspend );
    }

    private void verifyOverrideAppliesAndCheckForReferral( PremiumPage premiumPage,
        String expectedPremiumAdjustmentAmount )
    {
        // Apply a premium override of expectedPremiumAdjustmentAmount
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverridePremiumActivity( premiumPage,
                policyTotalPremium.add(
                    NumberConverter.convertCurrencyToBigDecimal( expectedPremiumAdjustmentAmount ) )
                    .toString() ) );
        premiumPage.assertReferralRules( "R035" );
    }

}
