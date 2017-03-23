package au.com.cgu.harvest.automation.scenario.farmmotor.endorsements;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtPolicyLevelActivity;
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
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.VehicleDetailPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Verify Policy Level Adj% has no impact on deleted vehicle in Farm Motor Endorsement Transaction Scenario" )
public class AssertPolicyLevelAdjustmentDoesNotImpactonDeletedVehicleInEndorsementScenario extends
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

        AgriculturalVehiclePage agriculturalVehiclePage =
            performActivity( new CreateAgriculturalVehicleActivity( vehiclePage ) );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( agriculturalVehiclePage ) );
        verifyPositiveAdjustmentPercentagePolicyLevel( premiumPage, "5.00%" );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( vehiclePage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        assertPolicyAdjustmentPercentage( premiumPage, "5.00%" );

        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 1 ) );
        vehiclePage.selectNonComprehensiveCover( "Fire, Theft and Third Party Property Damage" );

        agriculturalVehiclePage =
            performActivity( new EditAgriculturalVehicleActivity( vehiclePage, 2 ) );
        VehicleSummaryPage vehicleSummaryPage =
            performActivity( new DeleteVehicleActivity< VehicleDetailPage >(
                agriculturalVehiclePage ) );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );
        assertPolicyAdjustmentPercentage( premiumPage, "5.00%" );
        changePolicyLevelAdjustmentAndCheckNoImpactOnDeletedVehicle( premiumPage, "10.00" );
    }

    private void changePolicyLevelAdjustmentAndCheckNoImpactOnDeletedVehicle(
        PremiumPage premiumPage, String policyLevelAdjPercentage )
    {
        // chk premium before change in policyLevelAdj%
        Premium firstVehiclePremiumbeforeOverride = premiumPage.getPremiumForRow( 1 );
        Premium secondVehiclePremiumbeforeOverride = premiumPage.getPremiumForRow( 2 );

        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                policyLevelAdjPercentage ) );

        // chk premium before change in policyLevelAdj%

        Premium firstVehiclePremiumAfterOverride = premiumPage.getPremiumForRow( 1 );
        Premium secondVehiclePremiumAfterOverride = premiumPage.getPremiumForRow( 2 );

        // compare change in premium for first vehicle and no change in premium for deleted vehicle
        PremiumAssert.assertTotalPremiumIncreased( firstVehiclePremiumbeforeOverride,
            firstVehiclePremiumAfterOverride );
        PremiumAssert.assertTotalPremiumUnchanged( secondVehiclePremiumbeforeOverride,
            secondVehiclePremiumAfterOverride );

    }

    private void assertPolicyAdjustmentPercentage( PremiumPage premiumPage,
        String policyLevelAdjPercentage )
    {
        Assert.assertEquals( policyLevelAdjPercentage, premiumPage.getPolicyAdjustmentPercentage() );

    }

    private void verifyPositiveAdjustmentPercentagePolicyLevel( PremiumPage premiumPage,
        String policyLevelAdjPercentage )
    {
        Premium autoRatedFirstVehicle = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehicle = premiumPage.getPremiumForRow( 2 );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                policyLevelAdjPercentage ) );
        Premium autoRatedFirstVehicleAfterPolicyLevelAdj = premiumPage.getPremiumForRow( 1 );
        Premium autoRatedSecondVehicleAfterPolicyLevelAdj = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumIncreased( autoRatedFirstVehicle,
            autoRatedFirstVehicleAfterPolicyLevelAdj );
        PremiumAssert.assertTotalPremiumIncreased( autoRatedSecondVehicle,
            autoRatedSecondVehicleAfterPolicyLevelAdj );

        BigDecimal policyTotalPremiumAfterAdjustmentPercentage =
            premiumPage.getPolicyTotalPremium();
    }
}
