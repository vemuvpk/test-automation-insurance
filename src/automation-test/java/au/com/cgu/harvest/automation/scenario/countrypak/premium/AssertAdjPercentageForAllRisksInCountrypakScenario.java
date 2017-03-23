package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessInterruptionActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessLiabilityActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStock2Activity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddMachineryBreakdownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPersonalIncomeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddSecondDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddSecondFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithFarmContentsAndFarmMachinery;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtPolicyLevelActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtRiskLevelActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelDomesticBuildingPage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelFarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Risk Adjustment percentage is reset for Countrypak Scenario" )
public class AssertAdjPercentageForAllRisksInCountrypakScenario extends
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

        situationLevelSectionPage =
            performActivity( new AddASituationActivity( situationLevelSectionPage ) );

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );
        domesticBuildingAndContentsPage =
            performActivity( new AddSecondDwellingType( domesticBuildingAndContentsPage ) );

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStockActivity( farmPropertyPage ) );

        hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStock2Activity( hayFencesLiveStockPage ) );

        farmPropertyPage =
            performActivity( new AddSecondFarmPropertyActivity( hayFencesLiveStockPage ) );

        PolicyLevelDomesticBuildingPage policyLevelDomesticbuilding =
            performActivity( new AddPolicyLevelDomesticBuildingActivity( farmPropertyPage ) );

        PolicyLevelFarmPropertyPage policyLevelFarmProperty =
            performActivity( new AddPolicyLevelFarmPropertyActivity( policyLevelDomesticbuilding ) );

        FarmMachineryAndWorkingDogsPage farmMachineryPage =
            performActivity( new AddFarmMachineryActivity( policyLevelFarmProperty ) );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithFarmContentsAndFarmMachinery(
                farmMachineryPage ) );

        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new AddBusinessInterruptionActivity( theftPage ) );

        BusinessLiabilityPage businessLiabilityPage =
            performActivity( new AddBusinessLiabilityActivity( businessInterruptionPage ) );

        MachineryBreakdownPage machineryBreakdownPage =
            performActivity( new AddMachineryBreakdownActivity( businessLiabilityPage ) );

        PersonalIncomePage personalIncomePage =
            performActivity( new AddPersonalIncomeActivity( machineryBreakdownPage ) );
        personalIncomePage =
            performActivity( new AddPersonalIncomeActivity( personalIncomePage, "Second Person",
                true ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( personalIncomePage ) );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( roadTransitPage ) );

        verifyPositiveRiskLevelAdjustmentAtSectionLevel( premiumPage );
        verifyNegativeRiskLevelAdjustmentAtSectionLevel( premiumPage );
        verifyPositiveAdjustmentPercentageAtPolicyLevel( premiumPage );
        verifyNegativeAdjustmentPercentageAtPolicyLevel( premiumPage );

    }

    private void verifyPositiveRiskLevelAdjustmentAtSectionLevel(
        PremiumPage premiumPage )
    {
        final String expectedRiskLevelAdjustmentPercentage = "2.0";
        // Get the original values before Adjustment percentage
        Premium autoRatedSection = premiumPage.getPremiumForRow( 3 );

        // Apply Situation Level Adjustment Percentage
        Premium totalPremium = premiumPage.getPolicyPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 3 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 4 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 5 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 6 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 7 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 8 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 9 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 10 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 11 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 12 ),
                expectedRiskLevelAdjustmentPercentage ) );

        Premium autoRatedSectionAfterAdjPercentage = premiumPage.getPremiumForRow( 3 );
        Premium totalPremiumAfterAdjPercentage = premiumPage.getPolicyPremium();

        PremiumAssert.assertTotalPremiumIncreased( autoRatedSection,
            autoRatedSectionAfterAdjPercentage );

        PremiumAssert.assertTotalPremiumIncreased( totalPremium,
            totalPremiumAfterAdjPercentage );

        premiumPage.reset();

        Premium autoRatedSectionAfterReset = premiumPage.getPremiumForRow( 3 );
        PremiumAssert.assertTotalPremiumUnchanged( autoRatedSection, autoRatedSectionAfterReset );
    }

    private void verifyNegativeRiskLevelAdjustmentAtSectionLevel(
        PremiumPage premiumPage )
    {
        final String expectedRiskLevelAdjustmentPercentage = "-2.0";

        // Get the original values before Adjustment percentage
        Premium autoRatedSection = premiumPage.getPremiumForRow( 3 );

        // Apply Situation Level Adjustment Percentage
        Premium totalPremium = premiumPage.getPolicyPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 3 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 4 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 5 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 6 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 7 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 8 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 9 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 10 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 11 ),
                expectedRiskLevelAdjustmentPercentage ) );
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 12 ),
                expectedRiskLevelAdjustmentPercentage ) );

        Premium autoRatedSectionAfterAdjPercentage = premiumPage.getPremiumForRow( 3 );
        Premium totalPremiumAfterAdjPercentage = premiumPage.getPolicyPremium();

        PremiumAssert.assertTotalPremiumDecreased( autoRatedSection,
            autoRatedSectionAfterAdjPercentage );

        PremiumAssert.assertTotalPremiumDecreased( totalPremium,
            totalPremiumAfterAdjPercentage );

        premiumPage.reset();

        Premium autoRatedSectionAfterReset = premiumPage.getPremiumForRow( 3 );
        PremiumAssert.assertTotalPremiumUnchanged( autoRatedSection, autoRatedSectionAfterReset
            );
    }

    private void verifyPositiveAdjustmentPercentageAtPolicyLevel(
        PremiumPage premiumPage )
    {
        final String expectedPolicyLevelAdjustmentPercentage = "2.0";
        // Get the original values before Adjustment percentage
        Premium autoRatedSection = premiumPage.getPolicyPremium();

        // Apply Policy Level Adjustment Percentage
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                expectedPolicyLevelAdjustmentPercentage ) );

        Premium autoRatedSectionAfterAdjPercentage = premiumPage.getPolicyPremium();

        PremiumAssert.assertTotalPremiumIncreased( autoRatedSection,
            autoRatedSectionAfterAdjPercentage );

        premiumPage.reset();

        Premium autoRatedSectionAfterReset = premiumPage.getPolicyPremium();
        PremiumAssert.assertTotalPremiumUnchanged( autoRatedSection, autoRatedSectionAfterReset );
    }

    private void verifyNegativeAdjustmentPercentageAtPolicyLevel(
        PremiumPage premiumPage )
    {
        final String expectedPolicyLevelAdjustmentPercentage = "-2.0";
        // Get the original values before Adjustment percentage
        Premium autoRatedSection = premiumPage.getPolicyPremium();

        // Apply Policy Level Adjustment Percentage
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                expectedPolicyLevelAdjustmentPercentage ) );

        Premium autoRatedSectionAfterAdjPercentage = premiumPage.getPolicyPremium();

        PremiumAssert.assertTotalPremiumDecreased( autoRatedSection,
            autoRatedSectionAfterAdjPercentage );

        premiumPage.reset();

        Premium autoRatedSectionAfterReset = premiumPage.getPolicyPremium();
        PremiumAssert.assertTotalPremiumUnchanged( autoRatedSection, autoRatedSectionAfterReset );
    }
}
