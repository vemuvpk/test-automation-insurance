package au.com.cgu.harvest.automation.scenario.countrypak.endorsements;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteDomesticBuildingFromNavTreeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendBusinessActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Restore the inforce values for any deleted Section during Endorsement transaction for Countrypak Scenario" )
public class AssertInForceValuesWhenSectionDeletedInEndorsementTransactionScenario extends
    AbstractScenario
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

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            addASituationWithDomesticBuilding( insuranceHistoryPage );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( domesticBuildingAndContentsPage ) );

        Premium totalPremiumForSection =
            premiumPage.getPremiumGrid().getFooterRow().getPolicyTotalPremium();

        policyDetailPage = doAnEndorsementTransaction( premiumPage );

        // Navigate to Section details page and change one of the rating factors to trigger change
        // in premium
        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( policyDetailPage, 1 ) );
        domesticBuildingAndContentsPage.setAdditionalBusinessContentsSumInsured( "20000" );

        // Suspend, save and edit the policy
        policyDetailPage = sudpendAndEditThePolicy( domesticBuildingAndContentsPage );

        // delete the Section and chk that the transaction premium refund is same as the actual
        // original premium
        deleteSectionAndAssertTransactionPermiumRefund( policyDetailPage,
            premiumPage, totalPremiumForSection );
    }

    private PolicyDetailPage doAnEndorsementTransaction( PremiumPage premiumPage )
    {
        PolicyDetailPage policyDetailPage;
        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( premiumPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( finishPage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        return policyDetailPage;
    }

    private DomesticBuildingAndContentsPage addASituationWithDomesticBuilding(
        CountrypakInsuranceHistoryPage insuranceHistoryPage )
    {
        SituationDetailPage situationLevelSectionPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                insuranceHistoryPage ) );

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );
        return domesticBuildingAndContentsPage;
    }

    private void deleteSectionAndAssertTransactionPermiumRefund(
        PolicyDetailPage policyDetailPage, PremiumPage premiumPage, Premium totalPremiumForSection )
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage;
        domesticBuildingAndContentsPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( policyDetailPage, 1 ) );
        policyDetailPage =
            performActivity( new DeleteDomesticBuildingFromNavTreeActivity(
                domesticBuildingAndContentsPage, 1 ) );

        premiumPage =
            performActivity( new ViewPremiumActivity( premiumPage ) );

        Premium totalPremiumForSectionAfterEdit =
            premiumPage.getPremiumGrid().getFooterRow().getPolicyTotalPremium();

        Assert.assertEquals( false,
            totalPremiumForSection.getTotalPremium()
                .add( totalPremiumForSectionAfterEdit.getTotalPremium() )
                .equals( PremiumAssert.DECIMAL_ZERO ) );
    }

    private PolicyDetailPage sudpendAndEditThePolicy(
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage )
    {
        PolicyDetailPage policyDetailPage;
        NewBusinessPage newBusinessPage;
        newBusinessPage =
            performActivity( new SuspendBusinessActivity< HarvestPage >(
                domesticBuildingAndContentsPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        return policyDetailPage;
    }
}
