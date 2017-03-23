package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddSecondBuildingTypeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertForFarmBuildingsInFarmPropertyPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.DeleteFarmBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.EditFarmBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.EditFarmBuildingAndChangeBuildingTypeAndSumInsuredActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Add, Edit and Delete multiple Farm Buildings Scenario" )
public class AddEditAndDeleteMultipleFarmBuildingsScenario extends AbstractScenario
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

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationDetailPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );
        farmPropertyPage =
            performActivity( new AddSecondBuildingTypeActivity( farmPropertyPage ) );

        farmPropertyPage =
            performActivity( new AssertForFarmBuildingsInFarmPropertyPageActivity(
                farmPropertyPage,
                1, "Cool Room", "Brick/Concrete Floors", "No", "$500", "$5,000" ) );
        farmPropertyPage =
            performActivity( new AssertForFarmBuildingsInFarmPropertyPageActivity(
                farmPropertyPage,
                2, "Aircraft Hangar", "Brick/Concrete Floors", "Yes", "$1,000", "$1,000" ) );

        farmPropertyPage =
            performActivity( new EditFarmBuildingActivity( farmPropertyPage, 1 ) );

        farmPropertyPage =
            performActivity( new AssertForFarmBuildingsInFarmPropertyPageActivity(
                farmPropertyPage,
                1, "Cool Room", "Brick/Concrete Floors", "No", "$500", "$5,000" ) );
        farmPropertyPage =
            performActivity( new AssertForFarmBuildingsInFarmPropertyPageActivity(
                farmPropertyPage,
                2, "Aircraft Hangar", "Brick/Concrete Floors", "Yes", "$1,000", "$1,000" ) );

        farmPropertyPage =
            performActivity( new DeleteFarmBuildingActivity( farmPropertyPage, 2 ) );
        farmPropertyPage =
            performActivity( new AssertForFarmBuildingsInFarmPropertyPageActivity(
                farmPropertyPage,
                1, "Cool Room", "Brick/Concrete Floors", "No", "$500", "$5,000" ) );
        farmPropertyPage =
            performActivity( new EditFarmBuildingAndChangeBuildingTypeAndSumInsuredActivity(
                farmPropertyPage, 1 ) );
        farmPropertyPage =
            performActivity( new AssertForFarmBuildingsInFarmPropertyPageActivity(
                farmPropertyPage,
                1, "Cool Room", "Brick/Concrete Floors", "No", "$5,000", "$5,000" ) );

    }
}
