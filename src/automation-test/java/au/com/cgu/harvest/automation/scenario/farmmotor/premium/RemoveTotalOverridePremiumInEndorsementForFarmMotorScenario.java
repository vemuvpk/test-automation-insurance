package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtPolicyLevelActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverridePremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
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
@Scenario( "Assert Premium override done as part of new Business is removed in Endorsement transaction Scenario" )
public class RemoveTotalOverridePremiumInEndorsementForFarmMotorScenario extends AbstractScenario
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

        verifyOverrideAppliesToCountrypakPolicy( premiumPage );
        verifyPositiveAdjustmentPercentagePolicyLevel( premiumPage, "5.5" );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( premiumPage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        premiumPage =
            performActivity( new ViewPremiumActivity( policyDetailPage ) );

        changeAdjPercentageAndVerifyItIsAppliedToTheSection( premiumPage, "10" );

    }

    public void verifyOverrideAppliesToCountrypakPolicy( PremiumPage premiumPage )
    {
        final String expectedPremiumAdjustmentAmount = "$1.00";

        // Apply a premium override of $1.00
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverridePremiumActivity( premiumPage,
                policyTotalPremium.add(
                    NumberConverter.convertCurrencyToBigDecimal( expectedPremiumAdjustmentAmount ) )
                    .toString() ) );

        BigDecimal premiumAfterOverride = premiumPage.getPolicyTotalPremium();

    }

    private void verifyPositiveAdjustmentPercentagePolicyLevel( PremiumPage premiumPage,
        String policyLevelAdjPercentage )
    {
        Premium autoRatedFirstVehicle = premiumPage.getPremiumForRow( 1 );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                policyLevelAdjPercentage ) );
        Premium autoRatedFirstVehicleAfterPolicyLevelAdj = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumIncreased( autoRatedFirstVehicle,
            autoRatedFirstVehicleAfterPolicyLevelAdj );
        BigDecimal policyTotalPremiumAfterAdjustmentPercentage =
            premiumPage.getPolicyTotalPremium();

    }

    private void changeAdjPercentageAndVerifyItIsAppliedToTheSection( PremiumPage premiumPage,
        String policyLevelAdjPercentage )
    {
        Premium autoRatedFirstVehicle = premiumPage.getPremiumForRow( 1 );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                policyLevelAdjPercentage ) );
        Premium autoRatedFirstVehicleAfterPolicyLevelAdj = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumIncreased( autoRatedFirstVehicle,
            autoRatedFirstVehicleAfterPolicyLevelAdj );
        BigDecimal policyTotalPremiumAfterAdjustmentPercentage =
            premiumPage.getPolicyTotalPremium();

    }
}
