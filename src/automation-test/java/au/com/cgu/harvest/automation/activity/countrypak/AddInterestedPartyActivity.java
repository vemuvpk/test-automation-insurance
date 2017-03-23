package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPage;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPopUp;

@Activity( "Add Interested Party Activity" )
public class AddInterestedPartyActivity extends AbstractActivity< InterestedPartiesPage >
{
    private InterestedPartiesPage _page;

    private String _sectionString;

    public AddInterestedPartyActivity( InterestedPartiesPage interestedPartiesPage, String section )
    {
        _page = interestedPartiesPage;
        _sectionString = section;
    }

    public AddInterestedPartyActivity( InterestedPartiesPage interestedPartiesPage )
    {
        _page = interestedPartiesPage;
    }

    @Override
    protected InterestedPartiesPage execute()
    {
        InterestedPartiesPopUp interestedPartiesPopUp = _page.addInterestedParty();
        // TESTING:: CFMC-277
        // When above JIRA is resolved uncomment the below 2 lines and delete lines 45 and 46

        // interestedPartiesPopUp.setInterestedPartyName( "AGC" );
        // interestedPartiesPopUp.setFinanceType( "HIRE_PURCHASE" );

        if ( _sectionString == null || _sectionString.isEmpty() )
        {
            interestedPartiesPopUp.attachToSection( "Section 10 - Road Transit" );
        }
        else
        {
            interestedPartiesPopUp.attachToSection( _sectionString );
        }

        interestedPartiesPopUp.setInterestedPartyName( "AGC" );
        interestedPartiesPopUp.setFinanceType( "HIRE_PURCHASE" );

        interestedPartiesPopUp.ok();

        return _page;
    }
}
