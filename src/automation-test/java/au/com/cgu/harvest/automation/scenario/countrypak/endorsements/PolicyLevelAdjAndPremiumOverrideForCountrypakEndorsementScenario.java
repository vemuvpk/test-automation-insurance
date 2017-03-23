package au.com.cgu.harvest.automation.scenario.countrypak.endorsements;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AssertAndSetDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.AssertPolicyStageAndStatusInHeaderActivity;
import au.com.cgu.harvest.automation.activity.FinishPolicyForEndorsementActivity;
import au.com.cgu.harvest.automation.activity.ViewTotalPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithFarmContentsAndFarmMachinery;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToPolicyDetailPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtPolicyLevelActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverridePremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.NumberConverter;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.TotalPremiumPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "PH-1076, PH-1055" )
@Scenario( "To Check Policy Level Adj % and then Premium Override for Countrypak Endorsement Scenario" )
public class PolicyLevelAdjAndPremiumOverrideForCountrypakEndorsementScenario extends
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

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );

        FarmMachineryAndWorkingDogsPage farmMachineryPage =
            performActivity( new AddFarmMachineryActivity( domesticBuildingAndContentsPage ) );

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( premiumPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity(
                finishPage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new AssertPolicyStageAndStatusInHeaderActivity( policyDetailPage,
                "Endorsement", "Complete", "Intermediary Statement" ) );
        premiumPage = performActivity( new ViewPremiumActivity( policyDetailPage ) );
        // Apply Adj% at policy level and add a section
        verifyPositiveAdjustmentPercentagePolicyLevel( premiumPage, "5.5" );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithFarmContentsAndFarmMachinery( policyDetailPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );
        PremiumAssert.assertNonZeroPolicyAdjustmentPercentage( premiumPage );

        policyDetailPage =
            performActivity( new NavigateToPolicyDetailPageActivity( theftPage ) );

        policyDetailPage =
            performActivity( new AssertAndSetDutyOfDisclosureActivity( policyDetailPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );
        // Apply premium override on top of Policy Adj% - then suspend, save edit and make sure
        // premium override and policy level adj% is still present

        verifyOverrideAppliesToCountrypakSections( premiumPage );

        newBusinessPage =
            performActivity( new SuspendActivity( premiumPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        premiumPage =
            performActivity( new ViewPremiumActivity( policyDetailPage ) );

        verifyPremiumAdjustmentAmountIsNotNull( premiumPage );
        verifyNonZeroPolicyAdjustmentPercentage( premiumPage );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

        newBusinessPage =
            performActivity( new FinishPolicyForEndorsementActivity( finishPage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );
        PremiumAssert.assertNonZeroPolicyAdjustmentPercentage( premiumPage );
        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        newBusinessPage =
            performActivity( new FinishPolicyForEndorsementActivity( finishPage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
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

    private void verifyOverrideAppliesToCountrypakSections( PremiumPage premiumPage )
    {
        final String expectedPremiumAdjustmentAmount = "-$0.18";

        // Override premium at policy level

        // Apply a premium override of -$0.18
        Premium premiumBeforeOverride = premiumPage.getPolicyPremium();
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();

        // Checking values in total premium page
        TotalPremiumPage totalPremiumPage =
            performActivity( new ViewTotalPremiumActivity( premiumPage ) );
        String totalTransactionPremiumBeforeOverride =
            totalPremiumPage.getPremiumForThisTransaction();

        premiumPage = performActivity( new ViewPremiumActivity( totalPremiumPage ) );

        premiumPage =
            performActivity( new OverridePremiumActivity( premiumPage,
                policyTotalPremium.add(
                    NumberConverter.convertCurrencyToBigDecimal( expectedPremiumAdjustmentAmount ) )
                    .toString() ) );

        // Get the new values
        Premium premiumAfterOverride = premiumPage.getPolicyPremium();
        BigDecimal policyTotalPremiumAfterOverride = premiumPage.getPolicyTotalPremium();

        totalPremiumPage =
            performActivity( new ViewTotalPremiumActivity( premiumPage ) );
        String totalTransactionPremiumAfterOverride =
            totalPremiumPage.getPremiumForThisTransaction();

        premiumPage = performActivity( new ViewPremiumActivity( totalPremiumPage ) );

        PremiumAssert.assertTotalPremiumDecreased( premiumBeforeOverride,
            premiumAfterOverride );

        PremiumAssert.assertAmountCalculatedCorrectly( expectedPremiumAdjustmentAmount,
            premiumPage.getPremiumAdjustmentAmount() );

        totalPremiumPage =
            performActivity( new ViewTotalPremiumActivity( premiumPage ) );

        PremiumAssert.assertLessThan( "total transaction premium",
            totalTransactionPremiumAfterOverride, totalTransactionPremiumBeforeOverride );
    }

    private void verifyPremiumAdjustmentAmountIsNotNull( PremiumPage premiumPage )
    {
        PremiumAssert.assertNonZeroPremiumAdjustmentAmount( premiumPage );
    }

    private void verifyNonZeroPolicyAdjustmentPercentage( PremiumPage premiumPage )
    {
        PremiumAssert.assertNonZeroPolicyAdjustmentPercentage( premiumPage );
    }
}
