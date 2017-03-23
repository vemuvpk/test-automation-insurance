package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationWithNoAnnualTurnoverActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddMachineryBreakdownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertPremiumsInMachineryBreakdownPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToMachineryBreakdownPageActivity;
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
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Premium Changes in Section 8-Machinery Breakdown When Postcode and Occupation is Changed for a Situation Scenario" )
public class AssertPremiumChangeWhenPostcodeChangedForSection8Scenario extends AbstractScenario
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

        MachineryBreakdownPage machineryBreakdownPage =
            performActivity( new AddMachineryBreakdownActivity(
                situationLevelSectionPage ) );

        machineryBreakdownPage = verifyPremiumChangesWhenPostcodeChanges( machineryBreakdownPage );

        machineryBreakdownPage =
            verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( machineryBreakdownPage );

        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 1 ) );

        situationLevelSectionPage =
            performActivity( new AddASituationWithNoAnnualTurnoverActivity(
                insuranceHistoryPage ) );
        machineryBreakdownPage = verifyNilPremiumForNilAnnualTurnover( machineryBreakdownPage );
    }

    private MachineryBreakdownPage verifyNilPremiumForNilAnnualTurnover(
        MachineryBreakdownPage machineryBreakdownPage )
    {
        SituationDetailPage situationLevelSectionPage;
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                machineryBreakdownPage ) );

        machineryBreakdownPage =
            performActivity( new NavigateToMachineryBreakdownPageActivity(
                situationLevelSectionPage ) );
        machineryBreakdownPage =
            performActivity( new AssertPremiumsInMachineryBreakdownPageActivity(
                machineryBreakdownPage, "", "", "" ) );

        return machineryBreakdownPage;
    }

    private MachineryBreakdownPage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy(
        MachineryBreakdownPage machineryBreakdownPage )
    {
        PolicyDetailPage policyDetailPage;
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( machineryBreakdownPage ) );
        Premium machineryBreakdown = premiumPage.getPremiumForRow( 1 );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( machineryBreakdownPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        premiumPage =
            performActivity( new ViewPremiumActivity( machineryBreakdownPage ) );
        Premium machineryBreakdownAfterSuspend = premiumPage.getPremiumForRow( 1 );
        PremiumAssert.assertTotalPremiumUnchanged( machineryBreakdown,
            machineryBreakdownAfterSuspend );

        return machineryBreakdownPage;
    }

    private MachineryBreakdownPage verifyPremiumChangesWhenPostcodeChanges(
        MachineryBreakdownPage machineryBreakdownPage )
    {
        SituationDetailPage situationLevelSectionPage;
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( machineryBreakdownPage ) );
        Premium machineryBreakDown = premiumPage.getPremiumForRow( 1 );
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                machineryBreakdownPage ) );

        situationLevelSectionPage.setSuburbStatePostcode( "Ballarat", "VIC", "3350" );

        premiumPage =
            performActivity( new ViewPremiumActivity( machineryBreakdownPage ) );
        Premium machineryBreakDownAfterPostcodeChange = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumChanged( machineryBreakDown,
            machineryBreakDownAfterPostcodeChange );
        return machineryBreakdownPage;
    }
}
