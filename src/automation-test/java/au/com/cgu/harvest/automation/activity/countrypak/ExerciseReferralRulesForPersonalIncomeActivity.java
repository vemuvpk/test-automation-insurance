package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Error and Referral rules for Personal Income page Activity" )
public class ExerciseReferralRulesForPersonalIncomeActivity
    extends
    AbstractActivity< PersonalIncomePage >
{
    private HarvestPage _page;

    public ExerciseReferralRulesForPersonalIncomeActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PersonalIncomePage execute()
    {

        PersonalIncomePage personalIncomePage =
            _page.getNavigationTree().navigateToPersonalIncome( 1 );
        _page.getNavigationTree().getSection( SectionType.PERSONAL_INCOME, 1 );
        AbstractScenario.getScenarioLogger().trace( "Started Error rules" );

        personalIncomePage.setNumberOfAccidentUnits( "2" );
        personalIncomePage.setNumberOfIllnessUnits( "3" );
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.NUMBER_OF_ILLNESS_UNITS_LOCATOR ), "E046" ) );

        personalIncomePage.setNumberOfAccidentUnits( "100" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.NUMBER_OF_ACCIDENT_UNITS_LOCATOR ), "E047" ) );

        personalIncomePage.setNumberOfIllnessUnits( "100" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.NUMBER_OF_ILLNESS_UNITS_LOCATOR ), "E047" ) );

        personalIncomePage.setNumberOfAccidentUnits( "31" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.NUMBER_OF_ACCIDENT_UNITS_LOCATOR ), "R013" ) );

        personalIncomePage.setNumberOfIllnessUnits( "31" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.NUMBER_OF_ILLNESS_UNITS_LOCATOR ), "R013" ) );

        personalIncomePage.setDateOfBirth( "01/01/1930" );
        personalIncomePage.setNumberOfAccidentUnits( "2" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.DATE_OF_BIRTH_LOCATOR ), "R014" ) );

        personalIncomePage.setDateOfBirth( "01/04/1956" );
        personalIncomePage.setNumberOfIllnessUnits( "2" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.DATE_OF_BIRTH_LOCATOR ), "R014" ) );

        personalIncomePage.setOccupationFullTimeFarming( "false" );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.IS_OCCUPATION_FULL_TIME_FARMING_LOCATOR ), "R018" ) );

        personalIncomePage.setMountaineeringOrRockClimbing();
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.MOUNTAINEERING_OR_ROCK_CLIMBING_XPATH_LOCATOR ),
            "R011" ) );

        AbstractScenario.getScenarioLogger().trace( "Finished Error rules" );
        return personalIncomePage;
    }
}
