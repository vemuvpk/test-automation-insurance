package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmMachineryPageActivity;
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
@Scenario( "See Risk and premium calculated for Section 3 - Farm Machinery scenario" )
public class SeeRiskAndPremiumForSection3FarmMachineryScenario extends AbstractScenario
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

        NewBusinessPage newBusinessPage;

        farmMachineryPage =
            verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( farmMachineryPage );

        verifyPremiumDecreasesWhenRatingFactorChanges( farmMachineryPage );

    }

    private void verifyPremiumDecreasesWhenRatingFactorChanges(
        FarmMachineryAndWorkingDogsPage farmMachineryPage )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );
        Premium farmMachinery = premiumPage.getPolicyPremium();
        farmMachineryPage =
            performActivity( new NavigateToFarmMachineryPageActivity( farmMachineryPage ) );
        farmMachineryPage.getFarmMachineryGrid().deleteNameValueRow( 1 );
        farmMachineryPage.getFarmMachineryGrid().deleteNameValueRow( 1 );

        premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );
        Premium farmMachineryAfterRatingFactorChange = premiumPage.getPolicyPremium();

        PremiumAssert.assertTotalPremiumDecreased( farmMachinery,
            farmMachineryAfterRatingFactorChange );

        newBusinessPage =
            performActivity( new SuspendActivity( farmMachineryPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );
        Premium farmMachineryAftereditingThePolicy = premiumPage.getPolicyPremium();

        PremiumAssert.assertTotalPremiumUnchanged( farmMachineryAfterRatingFactorChange,
            farmMachineryAftereditingThePolicy );
    }

    private FarmMachineryAndWorkingDogsPage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy(
        FarmMachineryAndWorkingDogsPage farmMachineryPage )
    {
        PolicyDetailPage policyDetailPage;

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( farmMachineryPage ) );

        Premium farmMachinery = premiumPage.getPolicyPremium();

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( farmMachineryPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        premiumPage =
            performActivity( new ViewPremiumActivity( farmMachineryPage ) );
        Premium farmMachineryAfteredittingThePolicy = premiumPage.getPolicyPremium();
        PremiumAssert.assertTotalPremiumUnchanged( farmMachinery,
            farmMachineryAfteredittingThePolicy );
        return farmMachineryPage;
    }
}
