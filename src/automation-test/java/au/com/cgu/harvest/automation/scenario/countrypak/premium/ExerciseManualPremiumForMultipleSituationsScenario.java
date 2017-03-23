package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingTypeWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessInterruptionWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessLiabilityWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingTypeWithManualPremium;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPersonalIncomeWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForRaedownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmPropertyPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToHayFencesPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Manual Premium For Multiple Countrypak Situations Scenario" )
public class ExerciseManualPremiumForMultipleSituationsScenario extends AbstractScenario
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
                situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingTypeWithManualPremiumActivity( farmPropertyPage ) );

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceWithManualPremiumActivity( situationLevelSectionPage ) );

        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new AddBusinessInterruptionWithManualPremiumActivity(
                situationLevelSectionPage ) );

        BusinessLiabilityPage businessLiabilityPage =
            performActivity( new AddBusinessLiabilityWithManualPremiumActivity(
                situationLevelSectionPage ) );

        PersonalIncomePage personalIncomePage =
            performActivity( new AddPersonalIncomeWithManualPremiumActivity(
                situationLevelSectionPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( hayFencesLiveStockPage ) );

        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity(
                policyDetailPage, 1 ) );
        ManuallyRatedDomesticBuildingPage.assertPremiumValues( domesticBuildingAndContentsPage );

        farmPropertyPage =
            performActivity( new NavigateToFarmPropertyPageActivity(
                policyDetailPage, 1 ) );
        ManuallyRatedFarmPropertyPage.assertPremiumValues( farmPropertyPage );
        hayFencesLiveStockPage =
            performActivity( new NavigateToHayFencesPageActivity( policyDetailPage ) );
        ManuallyRatedHayFencesPage.assertPremiumValues( hayFencesLiveStockPage );

    }
}
