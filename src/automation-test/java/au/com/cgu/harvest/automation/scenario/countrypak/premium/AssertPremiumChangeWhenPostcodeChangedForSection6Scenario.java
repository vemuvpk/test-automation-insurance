package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationWithNoAnnualTurnoverActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessInterruptionActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertPremiumsInBusinessInterruptionPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessInterruptionPageActivity;
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
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Premium Changes in Section 6-Business Interruption When Postcode and Occupation is Changed for a Situation Scenario" )
public class AssertPremiumChangeWhenPostcodeChangedForSection6Scenario extends AbstractScenario
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

        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new AddBusinessInterruptionActivity(
                situationLevelSectionPage ) );

        businessInterruptionPage =
            verifyPremiumChangesWhenPostcodeChanges( businessInterruptionPage );

        businessInterruptionPage =
            verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( businessInterruptionPage );

        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 1 ) );

        situationLevelSectionPage =
            performActivity( new AddASituationWithNoAnnualTurnoverActivity(
                insuranceHistoryPage ) );
        verifyNoPremiumForNilAnnualTurnover( businessInterruptionPage );
    }

    private void verifyNoPremiumForNilAnnualTurnover(
        BusinessInterruptionPage businessInterruptionPage )
    {
        SituationDetailPage situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                businessInterruptionPage ) );
        businessInterruptionPage =
            performActivity( new NavigateToBusinessInterruptionPageActivity(
                situationLevelSectionPage ) );
        businessInterruptionPage =
            performActivity( new AssertPremiumsInBusinessInterruptionPageActivity(
                businessInterruptionPage, "", "", "" ) );

    }

    private BusinessInterruptionPage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy(
        BusinessInterruptionPage businessInterruptionPage )
    {
        SituationDetailPage situationLevelSectionPage;
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( businessInterruptionPage ) );
        Premium businessInterruption = premiumPage.getPremiumForRow( 1 );
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( businessInterruptionPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        premiumPage =
            performActivity( new ViewPremiumActivity( businessInterruptionPage ) );
        Premium businessInterruptionAfterSuspend = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumUnchanged( businessInterruption,
            businessInterruptionAfterSuspend );
        return businessInterruptionPage;
    }

    private BusinessInterruptionPage verifyPremiumChangesWhenPostcodeChanges(
        BusinessInterruptionPage businessInterruptionPage )
    {
        SituationDetailPage situationLevelSectionPage;
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( businessInterruptionPage ) );
        Premium businessInterruption = premiumPage.getPremiumForRow( 1 );

        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                businessInterruptionPage ) );

        situationLevelSectionPage.setSuburbStatePostcode( "Ballarat", "VIC", "3350" );

        premiumPage =
            performActivity( new ViewPremiumActivity( businessInterruptionPage ) );
        Premium businessInterruptionAfterPostCodeChange = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumChanged( businessInterruption,
            businessInterruptionAfterPostCodeChange );
        return businessInterruptionPage;
    }
}
