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
import au.com.cgu.harvest.automation.activity.farmmotor.OverridePremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
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
@JiraReference( "PH-1076" )
@Scenario( "To Check Override Premium for Countrypak Endorsement Scenario" )
public class OverridePremiumForCountrypakEndorsementScenario extends
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

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage )
            );

        verifyOverrideAppliesToCountrypakSections( premiumPage );
        premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage )
            );
        verifyNonZeroCommissionAppliesToAllVehicles( premiumPage );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

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

        verifyPremiumAdjustmentIsNull( premiumPage );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithFarmContentsAndFarmMachinery( policyDetailPage ) );

        policyDetailPage =
            performActivity( new NavigateToPolicyDetailPageActivity( theftPage ) );

        policyDetailPage =
            performActivity( new AssertAndSetDutyOfDisclosureActivity( policyDetailPage ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        newBusinessPage =
            performActivity( new FinishPolicyForEndorsementActivity( finishPage ) );

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

    private void verifyNonZeroCommissionAppliesToAllVehicles( PremiumPage premiumPage )
    {

        PremiumAssert.assertNonZeroCommission( premiumPage );
        PremiumAssert.assertNonZeroCommissionGst( premiumPage );
    }

    private void verifyPremiumAdjustmentIsNull( PremiumPage premiumPage )
    {
        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );
    }
}
