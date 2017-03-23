package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;

@Activity( "Add a Personal Income Activity" )
public class AddPersonalIncomeWithManualPremiumActivity extends
    AbstractActivity< PersonalIncomePage >
{
    private HarvestPage _page;
    private String _personName;
    private boolean _addNew;

    public AddPersonalIncomeWithManualPremiumActivity( HarvestPage page )
    {
        this( page, "Test Name", false );
    }

    public AddPersonalIncomeWithManualPremiumActivity( HarvestPage page, boolean addNew )
    {
        this( page, "Test Name", addNew );
    }

    public AddPersonalIncomeWithManualPremiumActivity( HarvestPage page, String personName,
        boolean addNew )
    {
        _page = page;
        _personName = personName;
        _addNew = addNew;
    }

    @Override
    protected PersonalIncomePage execute()
    {

        PersonalIncomePage personalIncomePage = null;
        if ( !_addNew )
        {
            personalIncomePage = _page.getNavigationTree().navigateToPersonalIncome( 1 );
        }
        else
        {
            personalIncomePage = _page.getNavigationTree().addPerson();
        }

        personalIncomePage.setFullName( _personName );
        personalIncomePage.setDateOfBirth( "01/01/1981" );
        personalIncomePage.setGender( "Male" );

        personalIncomePage.setNumberOfAccidentUnits( "3" );
        personalIncomePage.setNumberOfIllnessUnits( "1" );
        personalIncomePage.setOccupationFullTimeFarming( "true" );
        personalIncomePage.setDoYouWishToReducePremium( "false" );
        personalIncomePage.setAreYouNonSmoker( "true" );
        personalIncomePage.setAreYouSufferingFromAccidentalInjury( "false" );
        personalIncomePage.setAreYouSufferingFromPreExistingIllness( "false" );

        // Check for E020
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ACCIDENT_BASE_PREMIUM_LOCATOR ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR ), "E020" ) );

        personalIncomePage.setAccidentBasePremium( "55.55" );
        personalIncomePage.setIllnessBasePremium( "55.55" );

        // Check for R062
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ACCIDENT_BASE_PREMIUM_LOCATOR ), "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( personalIncomePage.ILLNESS_BASE_PREMIUM_LOCATOR ), "R062" ) );

        return personalIncomePage;
    }
}
