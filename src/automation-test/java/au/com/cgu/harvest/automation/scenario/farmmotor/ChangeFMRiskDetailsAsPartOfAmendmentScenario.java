package au.com.cgu.harvest.automation.scenario.farmmotor;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertForNewQuestionInInsuranceHistoryPage;
import au.com.cgu.harvest.automation.activity.farmmotor.AssertMandatoryErrorForDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.DeleteVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditAgriculturalVehicleAndCheckErrorRulesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsCoverNoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToAgriculturalVehiclePageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToPolicyDetailPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SetAnsweredForAllDriversInInsuranceHistoryPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SetDutyOfDisclosureActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStageAndStatusActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertPolicyStateAfterAcceptanceFromSunriseActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.VehicleDetailPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Change Farm Motor Risk Details as part of an Amendment Transaction and Check for Rules Scenario" )
public class ChangeFMRiskDetailsAsPartOfAmendmentScenario extends
    AbstractScenario
{

    @Test
    public void execute()
    {
        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchFarmMotorActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        InsuranceHistoryPage insuranceHistoryPage =
            performActivity( new InsuranceHistoryActivity( policyDetailPage ) );

        PrivateMotorVehiclePage vehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity(
                insuranceHistoryPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity( vehiclePage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new AssertPolicyStageAndStatusActivity( policyDetailPage,
                "Farm Motor", "Amendment", "Complete" ) );

        AgriculturalVehiclePage agriculturalVehiclePage =
            performActivity( new CreateAgriculturalVehicleActivity( policyDetailPage ) );

        policyDetailPage =
            performActivity( new NavigateToPolicyDetailPageActivity( vehiclePage ) );
        policyDetailPage =
            performActivity( new AssertPolicyStageAndStatusActivity( policyDetailPage,
                "Farm Motor", "Amendment", "Draft" ) );

        agriculturalVehiclePage =
            performActivity( new NavigateToAgriculturalVehiclePageActivity( policyDetailPage ) );
        agriculturalVehiclePage =
            performActivity( new EditAgriculturalVehicleAndCheckErrorRulesActivity(
                agriculturalVehiclePage, 2 ) );
        VehicleSummaryPage vehicleSummaryPage =
            performActivity( new DeleteVehicleActivity< VehicleDetailPage >(
                agriculturalVehiclePage ) );

        agriculturalVehiclePage =
            performActivity( new CreateAgriculturalVehicleActivity( policyDetailPage ) );

        PremiumPage premiumPage =
            performActivity( new ViewPremiumActivity( vehicleSummaryPage ) );

        policyDetailPage =
            performActivity( new AssertMandatoryErrorForDutyOfDisclosureActivity( vehiclePage ) );
        policyDetailPage =
            performActivity( new SetDutyOfDisclosureActivity( policyDetailPage ) );

        insuranceHistoryPage =
            performActivity( new AssertForNewQuestionInInsuranceHistoryPage(
                policyDetailPage ) );
        insuranceHistoryPage =
            performActivity( new SetAnsweredForAllDriversInInsuranceHistoryPageActivity(
                insuranceHistoryPage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( insuranceHistoryPage ) );

        newBusinessPage =
            performActivity( new FinishPolicyAsCoverNoteActivity( insuranceHistoryPage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );
        newBusinessPage =
            performActivity( new AssertPolicyStateAfterAcceptanceFromSunriseActivity(
                newBusinessPage, "Amendment Unclosed Accepted" ) );
    }
}
