package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;

@Activity( "Add a Personal Income Activity" )
public class AddPersonalIncomeActivity extends
    AbstractActivity< PersonalIncomePage >
{
    private HarvestPage _page;
    private String _personName;
    private boolean _addNew;

    public AddPersonalIncomeActivity( HarvestPage page )
    {
        this( page, "Test Name", false );
    }

    public AddPersonalIncomeActivity( HarvestPage page, boolean addNew )
    {
        this( page, "Test Name", addNew );
    }

    public AddPersonalIncomeActivity( HarvestPage page, String personName, boolean addNew )
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

        return personalIncomePage;
    }
}
