package au.com.cgu.harvest.automation.scenario.countrypak.endorsements;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.AssertAndSetDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.AssertPolicyStageAndStatusInHeaderActivity;
import au.com.cgu.harvest.automation.activity.FinishPolicyForEndorsementWithReferralActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddMachineryBreakdownWithReferralActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithAReferralActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToPolicyDetailPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Endorse an Endorsement Transaction with new Referral in Countrypak Scenario" )
public class EndorseAnEndorsementTransactionWithReferralForCountrypakScenario extends
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
        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

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
        MachineryBreakdownPage machineryBreakdownPage =
            performActivity( new AddMachineryBreakdownWithReferralActivity( policyDetailPage ) );

        policyDetailPage =
            performActivity( new NavigateToPolicyDetailPageActivity( machineryBreakdownPage ) );

        policyDetailPage =
            performActivity( new AssertAndSetDutyOfDisclosureActivity( policyDetailPage ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyForEndorsementWithReferralActivity( finishPage ) );
        newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithAReferralActivity( policyDetailPage ) );

        policyDetailPage =
            performActivity( new NavigateToPolicyDetailPageActivity( machineryBreakdownPage ) );

        policyDetailPage =
            performActivity( new AssertAndSetDutyOfDisclosureActivity( policyDetailPage ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        referralPopup =
            performActivity( new FinishPolicyForEndorsementWithReferralActivity( finishPage ) );
        newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );
    }
}
