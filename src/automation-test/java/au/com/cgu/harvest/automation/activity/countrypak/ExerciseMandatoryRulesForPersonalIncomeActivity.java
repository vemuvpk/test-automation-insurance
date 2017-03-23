package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Error rule For Personal Income Page Activity" )
public class ExerciseMandatoryRulesForPersonalIncomeActivity
    extends
    AbstractActivity< PersonalIncomePage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForPersonalIncomeActivity(
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
        AbstractScenario.getScenarioLogger().trace( "Started mandatory rules" );
        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.FULL_NAME_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.DATE_OF_BIRTH_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.NUMBER_OF_ACCIDENT_UNITS_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.IS_OCCUPATION_FULL_TIME_FARMING_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.DO_YOU_WISH_TO_REDUCE_PREMIUM_LOCATOR ) ) );

        wait = new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.ARE_YOU_SUFFERING_FROM_ACCIDENT_LOCATOR ) ) );

        personalIncomePage.setOccupationFullTimeFarming( "false" );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.PLEASE_PROVIDE_DETAILS_OF_OCCUPATION_LOCATOR ) ) );

        personalIncomePage.setNumberOfAccidentUnits( "2" );
        personalIncomePage.setNumberOfIllnessUnits( "1" );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.ARE_YOU_NON_SMOKER_LOCATOR ) ) );

        personalIncomePage.setDoYouWishToReducePremium( "true" );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.NOMINATE_NUMBER_OF_WEEKS_LOCATOR ) ) );

        personalIncomePage.setAreYouSufferingFromAccidentalInjury( "true" );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.PROVIDE_DETAILS_TO_NATURE_OF_INJURY_LOCATOR ) ) );

        personalIncomePage.setAreYouSufferingFromPreExistingIllness( "true" );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.PROVIDE_DETAILS_TO_NATURE_OF_ILLNESS ) ) );

        personalIncomePage.setSnowOrIceSports();
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.HAZARDOUS_PURSUIT_LOADING_PERCENTAGE_LOCATOR ) ) );

        personalIncomePage.setYachtRacing();
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.DO_YOU_RECEIVE_PAYMENT_OR_REWARD_LOCATOR ) ) );
        personalIncomePage.setOtherSportingActivities();
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( personalIncomePage.PLEASE_SPECIFY_LOCATOR )
            ) );

        AbstractScenario.getScenarioLogger().trace( "Finished mandatory rules" );
        return personalIncomePage;
    }
}
