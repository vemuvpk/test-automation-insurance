package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.IPageWithInterestedParties;
import au.com.cgu.harvest.pages.farmmotor.InterestedPartySelectionPopup;

@Activity( "Add a Well Known Finance Type and Well Known Interested Party to a vehicle Activity" )
public class AddWellKnownFinanceTypeAndInterestedPartyActivity extends
    AbstractActivity< IPageWithInterestedParties >
{
    private IPageWithInterestedParties _page;

    public AddWellKnownFinanceTypeAndInterestedPartyActivity(
        IPageWithInterestedParties vehicleDetailPage )
    {
        _page = vehicleDetailPage;
        _page.setVehicleIsFinanced( "true" );
    }

    @Override
    protected IPageWithInterestedParties execute()
    {

        InterestedPartySelectionPopup interestedPartyPopup = _page.addInterestedParty();
        interestedPartyPopup.setWellKnownFinanceTypeWellKnownInterestedParty();
        return _page;
    }

}
