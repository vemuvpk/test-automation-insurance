package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumRow;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "CFMC-703" )
@Scenario( "Assert Risk Adjustment percentage is reset for DBAC Scenario" )
public class AssertAdjPercentageForDBACInCountrypakScenario extends
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
        domesticBuildingAndContentsPage.setAdditionalBusinessContentsSumInsured( "0" );

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( domesticBuildingAndContentsPage ) );

        verifyNegativeRiskLevelAdjustmentAtSectionLevel( premiumPage );

    }

    private void verifyNegativeRiskLevelAdjustmentAtSectionLevel(
        PremiumPage premiumPage )
    {
        final String expectedRiskLevelAdjustmentPercentage = "-2.0";

        Premium totalPremium = premiumPage.getPolicyPremium();

        PremiumRow parentRow1 = premiumPage.getPremiumGrid().getPremiumAtRow( 1 );

        parentRow1.toggleExpanded();
        assertTrue( "Row should be expanded", parentRow1.isExpanded() );

        PremiumRow subParentRow = parentRow1.getSubParentRow( 1 );
        subParentRow.setAdjustmentPercentageAtRiskLevel( expectedRiskLevelAdjustmentPercentage );

        Premium totalPremiumAfterAdjPercentage = premiumPage.getPolicyPremium();
        PremiumAssert.assertTotalPremiumDecreased( totalPremium,
            totalPremiumAfterAdjPercentage );

        premiumPage.reset();

        Premium totalPremiumAfterReset = premiumPage.getPolicyPremium();
        PremiumAssert.assertTotalPremiumUnchanged( totalPremium, totalPremiumAfterReset );

    }

}
