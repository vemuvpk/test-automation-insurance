package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationWithNoAnnualTurnoverActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithFarmContentsAndFarmMachinery;
import au.com.cgu.harvest.automation.activity.countrypak.AssertPremiumsInTheftPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToSituationLevelSectionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToTheftPageActivity;
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
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Premium Changes in Section 4-Theft When Postcode and Occupation is Changed for a Situation Scenario" )
public class AssertPremiumChangeWhenPostcodeChangedForSection4Scenario extends AbstractScenario
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

        TheftPage theftPage =
            performActivity( new AddTheftPageWithFarmContentsAndFarmMachinery(
                situationLevelSectionPage ) );

        theftPage = verifyPremiumChangesWhenPostcodeChanges( theftPage );

        theftPage = verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( theftPage );

        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 1 ) );

        situationLevelSectionPage =
            performActivity( new AddASituationWithNoAnnualTurnoverActivity(
                insuranceHistoryPage ) );
        verifyNilPremiumForNilAnnualTurnover( theftPage );
    }

    private void verifyNilPremiumForNilAnnualTurnover( TheftPage theftPage )
    {
        SituationDetailPage situationLevelSectionPage;
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( theftPage ) );

        theftPage =
            performActivity( new NavigateToTheftPageActivity( situationLevelSectionPage ) );
        theftPage =
            performActivity( new AssertPremiumsInTheftPageActivity( theftPage, "", "", "", "" ) );
    }

    private TheftPage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( TheftPage theftPage )
    {
        PolicyDetailPage policyDetailPage;
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( theftPage ) );
        Premium theft = premiumPage.getPremiumForRow( 1 );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( theftPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        premiumPage =
            performActivity( new ViewPremiumActivity( theftPage ) );
        Premium theftAfterSuspend = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumUnchanged( theft, theftAfterSuspend );
        return theftPage;
    }

    private TheftPage verifyPremiumChangesWhenPostcodeChanges( TheftPage theftPage )
    {
        SituationDetailPage situationLevelSectionPage;
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( theftPage ) );
        Premium theft = premiumPage.getPremiumForRow( 1 );
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( theftPage ) );

        situationLevelSectionPage.setSuburbStatePostcode( "Ballarat", "VIC", "3350" );

        premiumPage =
            performActivity( new ViewPremiumActivity( theftPage ) );
        Premium theftAfterPostcodeChange = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumChanged( theft, theftAfterPostcodeChange );
        return theftPage;
    }
}
