package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyBuildingTypePopUp;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "CFMC-845,CFMC-846" )
@Scenario( "Exercise new Referral Rules for Sum Insured and Inferior Construction in Building Type Popup and test new ANZSIC Declined codes" )
public class ExerciseRulesForFarmPropertySumInsuredAndInferiorConstructionScenario extends
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

        SituationDetailPage situationLevelSectionPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                policyDetailPage ) );

        Wait< WebDriver > wait = checkNewANZSICDeclinedCodes( situationLevelSectionPage );

        // Referral Rules R042 for Sum insured and inferior construction
        FarmPropertyBuildingTypePopUp farmBuildingTypePopUp =
            situationLevelSectionPage.getNavigationTree().navigateToFarmProperty( 1 )
                .addBuildingType();

        checkNewReferralRules( wait, farmBuildingTypePopUp );

    }

    private void checkNewReferralRules( Wait< WebDriver > wait,
        FarmPropertyBuildingTypePopUp farmBuildingTypePopUp )
    {
        farmBuildingTypePopUp.setBuildingType( "Cool Room" );
        farmBuildingTypePopUp.setBuildingSumInsured( "50001" );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.BUILDING_SUM_INSURED_LOCATOR ),
            "R042" ) );

        farmBuildingTypePopUp.setBuildingType( "Piggery" );
        farmBuildingTypePopUp.setBuildingSumInsured( "100001" );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.BUILDING_SUM_INSURED_LOCATOR ),
            "R042" ) );

        farmBuildingTypePopUp.setBuildingType( "Packing Shed" );
        farmBuildingTypePopUp.setBuildingSumInsured( "100001" );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.BUILDING_SUM_INSURED_LOCATOR ),
            "R042" ) );

        farmBuildingTypePopUp.setBuildingType( "Other" );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.BUILDING_TYPE_LOCATOR ),
            "R042" ) );

        farmBuildingTypePopUp.setBuildingType( "Workshop" );
        farmBuildingTypePopUp.setBuildingSumInsured( "500001" );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.BUILDING_SUM_INSURED_LOCATOR ),
            "R042" ) );

        farmBuildingTypePopUp.setConstructionType( "Other" );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.CONSTRUCTION_TYPE_LOCATOR ),
            "R042" ) );

        farmBuildingTypePopUp.setConstructionType( "Inferior" );
        farmBuildingTypePopUp.setBuildingSumInsured( "150001" );
        wait.until( Rule.isDisplayed(
            By.xpath( farmBuildingTypePopUp.CONSTRUCTION_TYPE_LOCATOR ),
            "R042" ) );
        FarmPropertyPage farmPropertyPage = farmBuildingTypePopUp.cancel();
    }

    private Wait< WebDriver > checkNewANZSICDeclinedCodes(
        SituationDetailPage situationLevelSectionPage )
    {
        situationLevelSectionPage.setOccupation( "0301B" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( situationLevelSectionPage.OCCUPATION_LOCATOR ),
            "D005" ) );

        situationLevelSectionPage.setOccupation( "0219J" );
        wait.until( Rule.isDisplayed( By.xpath( situationLevelSectionPage.OCCUPATION_LOCATOR ),
            "D005" ) );
        return wait;
    }
}
