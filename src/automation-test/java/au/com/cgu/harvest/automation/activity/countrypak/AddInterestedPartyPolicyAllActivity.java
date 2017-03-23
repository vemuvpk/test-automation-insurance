package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPage;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPopUp;

@Activity( "Add Interested Party Activity" )
public class AddInterestedPartyPolicyAllActivity extends AbstractActivity< InterestedPartiesPage >
{
    private InterestedPartiesPage _page;

    public AddInterestedPartyPolicyAllActivity( InterestedPartiesPage interestedPartiesPage )
    {
        _page = interestedPartiesPage;
    }

    @Override
    protected InterestedPartiesPage execute()
    {
        InterestedPartiesPopUp interestedPartiesPopUp = _page.addInterestedParty();

        interestedPartiesPopUp.setInterestedPartyName( "AGC" );
        interestedPartiesPopUp.setFinanceType( "HIRE_PURCHASE" );
        interestedPartiesPopUp.attachToPolicyAll();

        interestedPartiesPopUp.ok();

        return _page;
    }
}
