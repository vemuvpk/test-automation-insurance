package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationWithNoAnnualTurnoverActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertPremiumsInFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToFarmPropertyPageActivity;
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
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Premium Changes in Section 2 - Farm Property When Postcode is Changed for a Situation Scenario" )
public class AssertPremiumChangeWhenPostcodeChangedForSection2Scenario extends AbstractScenario
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

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        farmPropertyPage = verifyPremiumChangesWhenPostCodeChanges( farmPropertyPage );

        farmPropertyPage =
            verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( farmPropertyPage );

        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 1 ) );

        situationLevelSectionPage =
            performActivity( new AddASituationWithNoAnnualTurnoverActivity(
                insuranceHistoryPage ) );

        verifyNoPremiumForNilAnnualTurnOver( farmPropertyPage );

    }

    private void verifyNoPremiumForNilAnnualTurnOver( FarmPropertyPage farmPropertyPage )
    {
        SituationDetailPage situationLevelSectionPage;
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( farmPropertyPage ) );

        farmPropertyPage =
            performActivity( new NavigateToFarmPropertyPageActivity( situationLevelSectionPage, 1 ) );
        farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        farmPropertyPage =
            performActivity( new AssertPremiumsInFarmPropertyActivity( farmPropertyPage, "",
                "", "", "", "", "" ) );
    }

    private FarmPropertyPage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy(
        FarmPropertyPage farmPropertyPage )
    {
        PolicyDetailPage policyDetailPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( farmPropertyPage ) );
        Premium farmProperty = premiumPage.getPremiumForRow( 1 );
        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( farmPropertyPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        premiumPage = performActivity( new ViewPremiumActivity( farmPropertyPage ) );
        Premium farmPropertyAfterSuspend = premiumPage.getPremiumForRow( 1 );
        PremiumAssert.assertTotalPremiumUnchanged( farmProperty, farmPropertyAfterSuspend );
        return farmPropertyPage;
    }

    private FarmPropertyPage verifyPremiumChangesWhenPostCodeChanges(
        FarmPropertyPage farmPropertyPage )
    {
        SituationDetailPage situationLevelSectionPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( farmPropertyPage ) );
        Premium farmProperty = premiumPage.getPremiumForRow( 1 );
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( farmPropertyPage ) );

        situationLevelSectionPage.setSuburbStatePostcode( "Ballarat", "VIC", "3350" );
        premiumPage = performActivity( new ViewPremiumActivity( farmPropertyPage ) );
        Premium farmPropertyAfterPostcodeChange = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumChanged( farmProperty, farmPropertyAfterPostcodeChange );
        return farmPropertyPage;
    }
}
