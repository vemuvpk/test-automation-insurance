package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;

@Activity( "Add a Personal Income Activity" )
public class AddPersonActivity extends
    AbstractActivity< PersonalIncomePage >
{
    private HarvestPage _page;

    public AddPersonActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PersonalIncomePage execute()
    {
        PersonalIncomePage personalIncomePage =
            _page.getNavigationTree().addPerson();
        personalIncomePage.setFullName( "Second Person" );
        personalIncomePage.setDateOfBirth( "01/01/1982" );
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
