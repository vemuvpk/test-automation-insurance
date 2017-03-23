package au.com.cgu.harvest.automation.scenario.farmmotor.endorsements;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.GetCountOfRisksInTransactionPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
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
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Verify Override is reset when New vehicle is added in Endorsement Transaction Scenario" )
public class OverrideAsPartOfAnFMEndorsementByAddingVehicleScenario extends
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

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( vehiclePage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        vehiclePage = performActivity( new CreatePrivateMotorVehicleActivity(
            insuranceHistoryPage ) );
        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        applyPremiumOverrideToThePolicy( premiumPage );
        verifyOverrideIsResetWhenNewVehicleAdded( premiumPage );
        premiumPage =
            performActivity( new GetCountOfRisksInTransactionPremiumActivity( premiumPage, 3 ) );
    }

    private void verifyOverrideIsResetWhenNewVehicleAdded( PremiumPage premiumPage )
    {
        PrivateMotorVehiclePage vehiclePage;
        premiumPage = performActivity( new ViewPremiumActivity( premiumPage ) );
        String adjustmentAmount = premiumPage.getPremiumAdjustmentAmount();
        vehiclePage = performActivity( new CreatePrivateMotorVehicleActivity(
            premiumPage ) );
        premiumPage = performActivity( new ViewPremiumActivity( premiumPage ) );
        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );
    }

    public void applyPremiumOverrideToThePolicy( PremiumPage premiumPage )
    {
        final String expectedPremiumAdjustmentAmount = "$0.75";

        // Apply a premium override of "$0.75"
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverridePremiumActivity( premiumPage,
                policyTotalPremium.add(
                    NumberConverter.convertCurrencyToBigDecimal( expectedPremiumAdjustmentAmount ) )
                    .toString() ) );
    }
}
