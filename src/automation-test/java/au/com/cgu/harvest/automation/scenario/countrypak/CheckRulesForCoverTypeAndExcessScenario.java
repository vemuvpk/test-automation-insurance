package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingWithNoSpecifiedValuablesActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyWithNoSpecifiedItemsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertCoverTypeAndExcessInDomesticBuildingsPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertCoverTypeAndExcessInFarmPropertyPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteDomesticBuildingFromNavTreeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteFarmPropertyFromNavTreeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmPropertyPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Check Rules for Cover Type and Excess to be captured on Each Section 1 and 2 which is taken Scenario" )
public class CheckRulesForCoverTypeAndExcessScenario extends AbstractScenario
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

        SituationDetailPage situationDetailPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                policyDetailPage ) );

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationDetailPage ) );
        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationDetailPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        situationDetailPage =
            performActivity( new AddASituationActivity( situationDetailPage ) );
        farmPropertyPage =
            performActivity( new NavigateToFarmPropertyPageActivity( situationDetailPage, 2 ) );
        farmPropertyPage =
            performActivity( new AddFarmPropertyWithNoSpecifiedItemsActivity( farmPropertyPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );
        farmPropertyPage =
            performActivity( new AssertCoverTypeAndExcessInFarmPropertyPageActivity(
                farmPropertyPage, "Listed Events", "500.000" ) );

        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( farmPropertyPage, 2 ) );
        domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingWithNoSpecifiedValuablesActivity(
                domesticBuildingAndContentsPage ) );
        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );
        domesticBuildingAndContentsPage =
            performActivity( new AssertCoverTypeAndExcessInDomesticBuildingsPageActivity(
                domesticBuildingAndContentsPage, "Listed Events", "500.000" ) );

        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity(
                domesticBuildingAndContentsPage, 1 ) );
        domesticBuildingAndContentsPage.changeCoverTypeAndExcess( "Accidental Damage", "750.000" );

        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity(
                domesticBuildingAndContentsPage, 2 ) );
        domesticBuildingAndContentsPage =
            performActivity( new AssertCoverTypeAndExcessInDomesticBuildingsPageActivity(
                domesticBuildingAndContentsPage, "Accidental Damage", "750.000" ) );
        policyDetailPage =
            performActivity( new DeleteDomesticBuildingFromNavTreeActivity(
                domesticBuildingAndContentsPage, 1 ) );
        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity(
                domesticBuildingAndContentsPage, 2 ) );
        domesticBuildingAndContentsPage =
            performActivity( new AssertCoverTypeAndExcessInDomesticBuildingsPageActivity(
                domesticBuildingAndContentsPage, "Accidental Damage", "750.000" ) );

        farmPropertyPage =
            performActivity( new NavigateToFarmPropertyPageActivity(
                domesticBuildingAndContentsPage, 1 ) );
        farmPropertyPage.changeCoverTypeAndExcess( "Accidental Damage", "750.000" );
        farmPropertyPage =
            performActivity( new NavigateToFarmPropertyPageActivity( farmPropertyPage, 2 ) );
        farmPropertyPage =
            performActivity( new AssertCoverTypeAndExcessInFarmPropertyPageActivity(
                farmPropertyPage, "Accidental Damage", "750.000" ) );
        policyDetailPage =
            performActivity( new DeleteFarmPropertyFromNavTreeActivity( farmPropertyPage, 1 ) );
        farmPropertyPage =
            performActivity( new NavigateToFarmPropertyPageActivity( farmPropertyPage, 2 ) );
        farmPropertyPage =
            performActivity( new AssertCoverTypeAndExcessInFarmPropertyPageActivity(
                farmPropertyPage, "Accidental Damage", "750.000" ) );
    }
}
