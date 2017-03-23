package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
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
import au.com.cgu.harvest.pages.PremiumRow;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Risk Adjustment percentage is reset for Countrypak Scenario" )
public class AssertAdjustmentPercentageAtPolicyAndRiskLevelIsResetForCountrypakScenario extends
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

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( situationLevelSectionPage ) );
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( roadTransitPage ) );

        verifyPositiveRiskLevelAdjustmentAtSectionLevel( premiumPage );

        verifyPositiveRiskLevelAdjustmentAtPolicyLevel( premiumPage );

        verifyNegativeRiskLevelAdjustmentAtSectionLevel( premiumPage );

        verifyNegativeRiskLevelAdjustmentAtPolicyLevel( premiumPage );

    }

    private void verifyPositiveRiskLevelAdjustmentAtSectionLevel(
        PremiumPage premiumPage )
    {
        final String expectedRiskLevelAdjustmentPercentage = "2.0";

        PremiumRow row = premiumPage.getPremiumGrid().getPremiumAtRow( 1 ).getChildRow( 1 );
        Premium cRow = row.getPremium();
        // Get the original values before Adjustment percentage
        Premium autoRatedSection = premiumPage.getPremiumForRow( 2 );

        // Apply Situation Level Adjustment Percentage
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 2 ),
                expectedRiskLevelAdjustmentPercentage ) );

        Premium autoRatedSectionAfterAdjPercentage = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumIncreased( autoRatedSection,
            autoRatedSectionAfterAdjPercentage );

        premiumPage.reset();

        Premium autoRatedSectionAfterReset = premiumPage.getPremiumForRow( 2 );
        PremiumAssert.assertTotalPremiumUnchanged( autoRatedSection, autoRatedSectionAfterReset );
    }

    private void verifyPositiveRiskLevelAdjustmentAtPolicyLevel(
        PremiumPage premiumPage )
    {
        final String expectedRiskLevelAdjustmentPercentage = "2.0";
        // Get the original values before Adjustment percentage
        Premium autoRatedSection = premiumPage.getPolicyPremium();

        // Apply Policy Level Adjustment Percentage
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                expectedRiskLevelAdjustmentPercentage ) );

        Premium autoRatedSectionAfterAdjPercentage = premiumPage.getPolicyPremium();

        PremiumAssert.assertTotalPremiumIncreased( autoRatedSection,
            autoRatedSectionAfterAdjPercentage );

        premiumPage.reset();

        Premium autoRatedSectionAfterReset = premiumPage.getPolicyPremium();
        PremiumAssert.assertTotalPremiumUnchanged( autoRatedSection, autoRatedSectionAfterReset );
    }

    private void verifyNegativeRiskLevelAdjustmentAtSectionLevel(
        PremiumPage premiumPage )
    {
        final String expectedRiskLevelAdjustmentPercentage = "-2.0";

        PremiumRow row = premiumPage.getPremiumGrid().getPremiumAtRow( 1 ).getChildRow( 1 );
        Premium cRow = row.getPremium();
        // Get the original values before Adjustment percentage
        Premium autoRatedSection = premiumPage.getPremiumForRow( 2 );

        // Apply Situation Level Adjustment Percentage
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtRiskLevelActivity( premiumPage,
                premiumPage.getPremiumGrid().getPremiumAtRow( 2 ),
                expectedRiskLevelAdjustmentPercentage ) );

        Premium autoRatedSectionAfterAdjPercentage = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumDecreased( autoRatedSection,
            autoRatedSectionAfterAdjPercentage );

        premiumPage.reset();

        Premium autoRatedSectionAfterReset = premiumPage.getPremiumForRow( 2 );
        PremiumAssert.assertTotalPremiumUnchanged( autoRatedSection, autoRatedSectionAfterReset );
    }

    private void verifyNegativeRiskLevelAdjustmentAtPolicyLevel(
        PremiumPage premiumPage )
    {
        final String expectedRiskLevelAdjustmentPercentage = "-2.0";
        // Get the original values before Adjustment percentage
        Premium autoRatedSection = premiumPage.getPolicyPremium();

        // Apply Policy Level Adjustment Percentage
        BigDecimal policyTotalPremium = premiumPage.getPolicyTotalPremium();
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                expectedRiskLevelAdjustmentPercentage ) );

        Premium autoRatedSectionAfterAdjPercentage = premiumPage.getPolicyPremium();

        PremiumAssert.assertTotalPremiumDecreased( autoRatedSection,
            autoRatedSectionAfterAdjPercentage );

        premiumPage.reset();

        Premium autoRatedSectionAfterReset = premiumPage.getPolicyPremium();
        PremiumAssert.assertTotalPremiumUnchanged( autoRatedSection, autoRatedSectionAfterReset );
    }
}
