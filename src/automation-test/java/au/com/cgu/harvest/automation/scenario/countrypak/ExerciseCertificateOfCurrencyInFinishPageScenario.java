package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddInterestedPartyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddInterestedPartyPolicyAllActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertCountOfRowsForCoCInFinishPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertInterestedPartiesActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ChangeCertificateOfCurrencyFromPolicyToRiskLevelActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.EditCertificateOfCurrencyRowActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ViewInterestedPartiesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CertificateOfCurrencyEditorPopup;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Certificate of Currency in Finish Page scenario" )
public class ExerciseCertificateOfCurrencyInFinishPageScenario extends AbstractScenario
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

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( policyDetailPage ) );
        InterestedPartiesPage interestedPartiesPage =
            performActivity( new ViewInterestedPartiesActivity( policyDetailPage ) );

        interestedPartiesPage =
            performActivity( new AddInterestedPartyPolicyAllActivity( interestedPartiesPage ) );

        String roadTransitSection = "Section 10 - Road Transit";

        interestedPartiesPage =
            performActivity( new AddInterestedPartyActivity( interestedPartiesPage,
                roadTransitSection ) );

        performActivity( new AssertInterestedPartiesActivity( interestedPartiesPage, 1,
            "Policy - All Sections" ) );
        performActivity( new AssertInterestedPartiesActivity( interestedPartiesPage, 2,
            roadTransitSection ) );

        FinishPage finishPage =
            performActivity( new
            ChangeCertificateOfCurrencyFromPolicyToRiskLevelActivity(
                interestedPartiesPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendActivity( finishPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );
        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

        CertificateOfCurrencyEditorPopup popup =
            performActivity( new EditCertificateOfCurrencyRowActivity( 1, finishPage ) );
        popup.getMarkedAttentionTo();
        popup.getAddressLine1();
        popup.getAdditionalInformation();
        popup.ok();

        policyDetailPage =
            performActivity( new DeleteRoadTransitActivity( finishPage ) );

        finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );
        finishPage =
            performActivity( new AssertCountOfRowsForCoCInFinishPageActivity( policyDetailPage, 1 ) );

    }
}
