package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddASituationWithNoAnnualTurnoverActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPersonalIncomeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertPremiumsInPersonalIncomePageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPersonalIncomePageActivity;
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
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Ignore
@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert Premium Changes in Section 9 - Personal Income When Postcode and Occupation is Changed for a Situation Scenario" )
public class AssertPremiumChangeWhenPostcodeChangedForSection9Scenario extends AbstractScenario
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

        PersonalIncomePage personalIncomePage =
            performActivity( new AddPersonalIncomeActivity(
                situationLevelSectionPage ) );

        personalIncomePage = verifyPremiumChangesWhenPostcodeChanges( personalIncomePage );

        personalIncomePage =
            verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy( personalIncomePage );

        verifyPremiumsAfterChangeInOccupation( personalIncomePage );

        policyDetailPage =
            performActivity( new DeleteSituationActivity( situationLevelSectionPage, 1 ) );

        situationLevelSectionPage =
            performActivity( new AddASituationWithNoAnnualTurnoverActivity(
                insuranceHistoryPage ) );

        verifyNilPremiumForNilAnnualTurnOver( personalIncomePage );
    }

    private void verifyNilPremiumForNilAnnualTurnOver( PersonalIncomePage personalIncomePage )
    {
        SituationDetailPage situationLevelSectionPage;
        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity( personalIncomePage ) );

        personalIncomePage =
            performActivity( new NavigateToPersonalIncomePageActivity( situationLevelSectionPage ) );
        personalIncomePage =
            performActivity( new AssertPremiumsInPersonalIncomePageActivity( personalIncomePage,
                "", "", "" ) );
    }

    private void verifyPremiumsAfterChangeInOccupation( PersonalIncomePage personalIncomePage )
    {
        SituationDetailPage situationLevelSectionPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( personalIncomePage ) );
        Premium personalIncome = premiumPage.getPremiumForRow( 1 );

        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                personalIncomePage ) );

        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                personalIncomePage ) );

        situationLevelSectionPage.setOccupation( "0115" );
        situationLevelSectionPage.setAnnualTurnOver( "100000" );

        premiumPage = performActivity( new ViewPremiumActivity( personalIncomePage ) );
        Premium personalIncomeAfterChangeInOccupation = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumUnchanged( personalIncome,
            personalIncomeAfterChangeInOccupation );

    }

    private PersonalIncomePage verifyNoChangeInPremiumAfterSuspendingAndEditingThePolicy(
        PersonalIncomePage personalIncomePage )
    {
        PolicyDetailPage policyDetailPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( personalIncomePage ) );
        Premium personalIncome = premiumPage.getPremiumForRow( 1 );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( personalIncomePage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( personalIncomePage ) );
        Premium personalIncomeAfterSuspend = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumUnchanged( personalIncome, personalIncomeAfterSuspend );

        return personalIncomePage;
    }

    private PersonalIncomePage verifyPremiumChangesWhenPostcodeChanges(
        PersonalIncomePage personalIncomePage )
    {
        SituationDetailPage situationLevelSectionPage;
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( personalIncomePage ) );

        Premium personalIncome = premiumPage.getPremiumForRow( 1 );

        situationLevelSectionPage =
            performActivity( new NavigateToSituationLevelSectionPageActivity(
                personalIncomePage ) );

        situationLevelSectionPage.setSuburbStatePostcode( "Ballarat", "VIC", "3350" );
        premiumPage = performActivity( new ViewPremiumActivity( personalIncomePage ) );
        Premium personalIncomeAfterPostcodeChange = premiumPage.getPremiumForRow( 1 );

        PremiumAssert.assertTotalPremiumChanged( personalIncome,
            personalIncomeAfterPostcodeChange );
        return personalIncomePage;
    }
}
