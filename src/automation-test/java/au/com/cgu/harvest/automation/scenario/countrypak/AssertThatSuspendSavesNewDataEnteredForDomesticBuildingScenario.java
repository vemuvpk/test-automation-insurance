package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddSecondDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AssertChangedDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertForDwellingsInDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.EditDwellingTypeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SuspendBusinessActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveEditAndEditRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert That Suspend saves New data enetered for a Domestic Building Scenario" )
public class AssertThatSuspendSavesNewDataEnteredForDomesticBuildingScenario extends
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

        SituationDetailPage situationDetailPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                policyDetailPage ) );

        DomesticBuildingAndContentsPage domesticBuildingPage =
            performActivity( new AddDomesticBuildingActivity( situationDetailPage ) );
        domesticBuildingPage =
            performActivity( new AddDwellingType( domesticBuildingPage ) );
        domesticBuildingPage =
            performActivity( new AddSecondDwellingType( domesticBuildingPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new SuspendBusinessActivity< SituationDetailPage >(
                situationDetailPage ) );
        policyDetailPage =
            performActivity( new SaveEditAndEditRiskDetailsActivity( newBusinessPage ) );

        domesticBuildingPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( policyDetailPage, 1 ) );

        domesticBuildingPage =
            performActivity( new AssertChangedDomesticBuildingActivity( domesticBuildingPage ) );

        domesticBuildingPage =
            performActivity( new EditDwellingTypeActivity( domesticBuildingPage, 1 ) );

        domesticBuildingPage =
            performActivity( new AssertForDwellingsInDomesticBuildingPageActivity(
                domesticBuildingPage,
                1, "Dwelling: Additional Details", "2010", "Brick/Concrete Floors", "$500",
                "$5,000", "$300" ) );
        domesticBuildingPage =
            performActivity( new AssertForDwellingsInDomesticBuildingPageActivity(
                domesticBuildingPage,
                2, "Caravan: Additional Details", "2011", "Brick/Concrete Floors", "$5,000",
                "$3,000", "$0" ) );

    }
}
