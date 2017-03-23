package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationWithNoAnnualTurnoverActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertPremiumsInFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmMachineryPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToSituationLevelSectionPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Premium Changes in Section3 When Postcode is Changed for a Situation Scenario" )
public class AssertPremiumChangeWhenPostcodeChangedForSection3Scenario extends AbstractScenario
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

        farmMachineryPage = verifyPremiumChangesWhenPostCodeChanges( farmMachineryPage );

        farmMachineryPage =
            verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( farmMachineryPage );

        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 1 ) );

        situationLevelSectionPage =
            performActivity( new AddASituationWithNoAnnualTurnoverActivity(
                insuranceHistoryPage ) );

        verifyNilPremiumForNilAnnualTurnOver( farmMachineryPage );

    }

    private void verifyNilPremiumForNilAnnualTurnOver(
        FarmMachineryAndWorkingDogsPage farmMachineryPage )
    {
        SituationDetailPage situationLevelSectionPage;
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( farmMachineryPage ) );

        farmMachineryPage =
            performActivity( new NavigateToFarmMachineryPageActivity( situationLevelSectionPage ) );
        farmMachineryPage =
            performActivity( new AssertPremiumsInFarmMachineryActivity( farmMachineryPage, "",
                "", "", "" ) );
    }

    private FarmMachineryAndWorkingDogsPage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy(
        FarmMachineryAndWorkingDogsPage farmMachineryPage )
    {
        PolicyDetailPage policyDetailPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );
        Premium farmMachinery = premiumPage.getPremiumForRow( 2 );
        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( farmMachineryPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );
        Premium farmMachineryAfterSuspend = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumUnchanged( farmMachinery, farmMachineryAfterSuspend );
        return farmMachineryPage;
    }

    private FarmMachineryAndWorkingDogsPage verifyPremiumChangesWhenPostCodeChanges(
        FarmMachineryAndWorkingDogsPage farmMachineryPage )
    {
        SituationDetailPage situationLevelSectionPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );
        Premium farmMachinery = premiumPage.getPremiumForRow( 2 );
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( farmMachineryPage ) );

        situationLevelSectionPage.setSuburbStatePostcode( "Ballarat", "VIC", "3350" );
        premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );
        Premium farmMachineryAfterPostcodeChange = premiumPage.getPremiumForRow( 2 );

        PremiumAssert.assertTotalPremiumChanged( farmMachinery, farmMachineryAfterPostcodeChange );
        return farmMachineryPage;
    }
}
