package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStock2Activity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddSecondDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
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
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Ignore( "Temporarily ignore the tests to see the green light" )
@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Risk Adjustment percentage is reset for Countrypak at Situation level Scenario" )
public class AssertAdjPercentageAtSituationLevelInCountrypakScenario extends
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

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( hayFencesLiveStockPage ) );

        verifyNegativeRiskLevelAdjustmentAtSectionLevel( premiumPage );

    }

    private void verifyNegativeRiskLevelAdjustmentAtSectionLevel(
        PremiumPage premiumPage )
    {
        final String expectedRiskLevelAdjustmentPercentage = "-2.0";

        Premium totalPremium = premiumPage.getPolicyPremium();

        // Get premium for Parent Row 1
        PremiumRow parentRow1 = premiumPage.getPremiumGrid().getPremiumAtRow( 1 );

        parentRow1.toggleExpanded();
        assertTrue( "Row should be expanded", parentRow1.isExpanded() );

        // Get Sub Parent Row 1 under Parent Row 1 and enter adj percentage
        PremiumRow subParentRow1 = parentRow1.getSubParentRow( 1 );
        subParentRow1.setAdjustmentPercentageAtRiskLevel( expectedRiskLevelAdjustmentPercentage );

        // Get Sub Parent Row 2 under Parent Row 1 and enter adj percentage
        parentRow1.toggleExpanded();
        PremiumRow subParentRow2 = parentRow1.getSubParentRow( 2 );
        subParentRow2.setAdjustmentPercentageAtRiskLevel( expectedRiskLevelAdjustmentPercentage );

        // Get Sub Parent Row 3 under Parent Row 1 and enter adj percentage
        parentRow1.toggleExpanded();
        PremiumRow subParentRow3 = parentRow1.getSubParentRow( 3 );
        subParentRow3.setAdjustmentPercentageAtRiskLevel( expectedRiskLevelAdjustmentPercentage );

        // Get premium for Parent Row 2
        PremiumRow parentRow2 = premiumPage.getPremiumGrid().getPremiumAtRow( 2 );

        parentRow2.toggleExpanded();
        assertTrue( "Row should be expanded", parentRow2.isExpanded() );

        PremiumRow childParentRow2 = parentRow2.getSubParentRow( 1 );
        childParentRow2.setAdjustmentPercentageAtRiskLevel( expectedRiskLevelAdjustmentPercentage );

        Premium totalPremiumAfterAdjPercentage = premiumPage.getPolicyPremium();
        PremiumAssert.assertTotalPremiumDecreased( totalPremium,
            totalPremiumAfterAdjPercentage );

        premiumPage.reset();

        Premium totalPremiumAfterReset = premiumPage.getPolicyPremium();
        PremiumAssert.assertTotalPremiumUnchanged( totalPremium, totalPremiumAfterReset );

    }

}
