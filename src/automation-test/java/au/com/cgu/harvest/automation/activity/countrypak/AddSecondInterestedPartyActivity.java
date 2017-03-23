package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPage;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPopUp;

@Activity( "Add Interested Party Activity" )
public class AddSecondInterestedPartyActivity extends AbstractActivity< InterestedPartiesPage >
{
    private InterestedPartiesPage _page;
    private String _sectionString;

    public AddSecondInterestedPartyActivity( InterestedPartiesPage interestedPartiesPage,
        String section )
    {
        _page = interestedPartiesPage;
        _sectionString = section;
    }

    @Override
    protected InterestedPartiesPage execute()
    {
        InterestedPartiesPopUp interestedPartiesPopUp = _page.addInterestedParty();

        if ( _sectionString == null || _sectionString.isEmpty() )
        {
            interestedPartiesPopUp.attachToSection( "Section 8 - Machinery Breakdown" );
            interestedPartiesPopUp.attachToSection( "Section 4 - Theft" );
        }
        else
        {
            interestedPartiesPopUp.attachToSection( _sectionString );
        }

        interestedPartiesPopUp.setInterestedPartyName( "BADL" );
        interestedPartiesPopUp.setFinanceType( "OTHER_LEASE" );

        interestedPartiesPopUp.ok();

        return _page;
    }
}
