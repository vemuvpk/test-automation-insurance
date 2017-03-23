package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationWithNoAnnualTurnoverActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessLiabilityActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertPremiumsInBusinessLiabilityPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToBusinessLiabilityPageActivity;
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
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Premium Changes in Section 7-Business Liability When Postcode and Occupation is Changed for a Situation Scenario" )
public class AssertPremiumChangeWhenPostcodeChangedForSection7Scenario extends AbstractScenario
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

        BusinessLiabilityPage businessLiabilityPage =
            performActivity( new AddBusinessLiabilityActivity(
                situationLevelSectionPage ) );

        businessLiabilityPage = verifyPremiumChangesWhenPostcodeChanges( businessLiabilityPage );

        businessLiabilityPage =
            verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( businessLiabilityPage );

        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 1 ) );

        situationLevelSectionPage =
            performActivity( new AddASituationWithNoAnnualTurnoverActivity(
                insuranceHistoryPage ) );
        verifyNilPremiumForNilAnnualTurnover( businessLiabilityPage );
    }

    private void verifyNilPremiumForNilAnnualTurnover( BusinessLiabilityPage businessLiabilityPage )
    {
        SituationDetailPage situationLevelSectionPage;
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                businessLiabilityPage ) );

        businessLiabilityPage =
            performActivity( new NavigateToBusinessLiabilityPageActivity(
                situationLevelSectionPage ) );
        businessLiabilityPage =
            performActivity( new AssertPremiumsInBusinessLiabilityPageActivity(
                businessLiabilityPage, "" ) );

    }

    private BusinessLiabilityPage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy(
        BusinessLiabilityPage businessLiabilityPage )
    {
        PolicyDetailPage policyDetailPage;
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( businessLiabilityPage ) );
        Premium businessLiability = premiumPage.getPremiumForRow( 1 );
        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( businessLiabilityPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        premiumPage =
            performActivity( new ViewPremiumActivity( businessLiabilityPage ) );
        Premium businessLiabilityAfterSuspend = premiumPage.getPremiumForRow( 1 );
        PremiumAssert
            .assertTotalPremiumUnchanged( businessLiability, businessLiabilityAfterSuspend );
        return businessLiabilityPage;
    }

    private BusinessLiabilityPage verifyPremiumChangesWhenPostcodeChanges(
        BusinessLiabilityPage businessLiabilityPage )
    {
        SituationDetailPage situationLevelSectionPage;
        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( businessLiabilityPage ) );
        Premium businessLiability = premiumPage.getPremiumForRow( 1 );
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                businessLiabilityPage ) );

        situationLevelSectionPage.setSuburbStatePostcode( "Ballarat", "VIC", "3350" );
        premiumPage =
            performActivity( new ViewPremiumActivity( businessLiabilityPage ) );
        Premium businessLiabilityAfterPostcodeChange = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumChanged( businessLiability,
            businessLiabilityAfterPostcodeChange );
        return businessLiabilityPage;
    }
}
