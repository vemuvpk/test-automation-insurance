package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingTypeWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessInterruptionWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessLiabilityWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingTypeWithManualPremium;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddMachineryBreakdownWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPersonalIncomeWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForRaedownActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Manual Premium Rules For Countrypak Policy with all Sections Scenario" )
public class AssertManualPremiumRulesForCountrypakSectionsScenario extends AbstractScenario
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
            performActivity( new CreateASituationForRaedownActivity(
                insuranceHistoryPage ) );

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingWithManualPremiumActivity(
                situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingTypeWithManualPremium( domesticBuildingAndContentsPage )
            );

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyWithManualPremiumActivity(
                domesticBuildingAndContentsPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingTypeWithManualPremiumActivity( farmPropertyPage ) );

        FarmMachineryAndWorkingDogsPage farmMachineryPage =
            performActivity( new AddFarmMachineryWithManualPremiumActivity(
                farmPropertyPage ) );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithManualPremiumActivity(
                farmMachineryPage ) );

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStockWithManualPremiumActivity( theftPage ) );

        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new AddBusinessInterruptionWithManualPremiumActivity(
                hayFencesLiveStockPage ) );

        BusinessLiabilityPage businessLiabilityPage =
            performActivity( new AddBusinessLiabilityWithManualPremiumActivity(
                businessInterruptionPage ) );

        MachineryBreakdownPage machineryBreakdownPage =
            performActivity( new AddMachineryBreakdownWithManualPremiumActivity(
                businessLiabilityPage ) );

        PersonalIncomePage personalIncomePage =
            performActivity( new AddPersonalIncomeWithManualPremiumActivity(
                situationLevelSectionPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitWithManualPremiumActivity( personalIncomePage ) );

    }
}
