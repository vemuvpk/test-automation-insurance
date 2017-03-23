package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddSecondDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AssertForDwellingsInDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteDwellingTypeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.EditDwellingAndChangeDwellingTypeAndSumInsuredActivity;
import au.com.cgu.harvest.automation.activity.countrypak.EditDwellingTypeActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Add, Edit and Delete Multiple Dwellings Scenario" )
public class AddEditAndDeleteMultipleDwellingsScenario extends AbstractScenario
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

        domesticBuildingPage =
            performActivity( new DeleteDwellingTypeActivity( domesticBuildingPage, 2 ) );
        domesticBuildingPage =
            performActivity( new AssertForDwellingsInDomesticBuildingPageActivity(
                domesticBuildingPage,
                1, "Dwelling: Additional Details", "2010", "Brick/Concrete Floors", "$500",
                "$5,000", "$300" ) );
        domesticBuildingPage =
            performActivity( new EditDwellingAndChangeDwellingTypeAndSumInsuredActivity(
                domesticBuildingPage, 1 ) );
        domesticBuildingPage =
            performActivity( new AssertForDwellingsInDomesticBuildingPageActivity(
                domesticBuildingPage,
                1, "Cottage: Additional Details", "2010", "Brick/Concrete Floors", "$5,000",
                "$5,000", "$300" ) );

    }
}
