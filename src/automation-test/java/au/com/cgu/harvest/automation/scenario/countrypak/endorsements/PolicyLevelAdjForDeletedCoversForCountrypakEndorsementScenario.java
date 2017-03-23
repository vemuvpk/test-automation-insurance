package au.com.cgu.harvest.automation.scenario.countrypak.endorsements;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AssertPolicyStageAndStatusInHeaderActivity;
import au.com.cgu.harvest.automation.activity.FinishPolicyForEndorsementActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.OverrideAdjustmentPercentageAtPolicyLevelActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "PH-1076, PH-1055" )
@Scenario( "To Check Policy Level Adj % is retained when a Cover is Deleted for Countrypak Endorsement Scenario" )
public class PolicyLevelAdjForDeletedCoversForCountrypakEndorsementScenario extends
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

        SituationDetailPage situationLevelSectionPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                insuranceHistoryPage ) );

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );

        FarmMachineryAndWorkingDogsPage farmMachineryPage =
            performActivity( new AddFarmMachineryActivity( domesticBuildingAndContentsPage ) );

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( farmMachineryPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( premiumPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity(
                finishPage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new AssertPolicyStageAndStatusInHeaderActivity( policyDetailPage,
                "Endorsement", "Complete", "Intermediary Statement" ) );
        premiumPage = performActivity( new ViewPremiumActivity( policyDetailPage ) );
        // Apply Adj% at policy level and Delete a section
        applyPositiveAdjustmentPercentagePolicyLevel( premiumPage, "5.5" );
        verifyAdjPercentageRetainedAfterSectionDeleted( premiumPage );
        verifyAdjPercentageInNextEndorsementTransaction( premiumPage );

    }

    private void applyPositiveAdjustmentPercentagePolicyLevel( PremiumPage premiumPage,
        String policyLevelAdjPercentage )
    {
        premiumPage =
            performActivity( new OverrideAdjustmentPercentageAtPolicyLevelActivity( premiumPage,
                policyLevelAdjPercentage ) );
    }

    private void verifyAdjPercentageRetainedAfterSectionDeleted( PremiumPage premiumPage )
    {
        PolicyDetailPage policyDetailPage =
            performActivity( new DeleteFarmMachineryActivity( premiumPage ) );

        premiumPage = performActivity( new ViewPremiumActivity( policyDetailPage ) );
        PremiumAssert.assertNonZeroPolicyAdjustmentPercentage( premiumPage );

        // Verify after suspending the policy

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( premiumPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        premiumPage =
            performActivity( new ViewPremiumActivity( policyDetailPage ) );
        PremiumAssert.assertNonZeroPolicyAdjustmentPercentage( premiumPage );
    }

    private void verifyAdjPercentageInNextEndorsementTransaction( PremiumPage premiumPage )
    {
        FinishPage finishpage =
            performActivity( new NavigateToFinishPageActivity( premiumPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyForEndorsementActivity( finishpage ) );
        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        PolicyDetailPage policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        premiumPage =
            performActivity( new ViewPremiumActivity( policyDetailPage ) );
        PremiumAssert.assertNonZeroPolicyAdjustmentPercentage( premiumPage );
    }

}
