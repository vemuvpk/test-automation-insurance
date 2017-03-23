package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.IPageWithInterestedParties;
import au.com.cgu.harvest.pages.farmmotor.InterestedPartySelectionPopup;

@Activity( "Add an Well known Finance Type and Other Interested Party to a vehicle Activity" )
public class AddWellKnownFinanceTypeAndOtherInterestedPartyActivity extends
    AbstractActivity< IPageWithInterestedParties >
{
    private IPageWithInterestedParties _page;

    public AddWellKnownFinanceTypeAndOtherInterestedPartyActivity(
        IPageWithInterestedParties vehicleDetailPage )
    {
        _page = vehicleDetailPage;
        _page.setVehicleIsFinanced( "true" );
    }

    @Override
    protected IPageWithInterestedParties execute()
    {
        InterestedPartySelectionPopup interestedPartyPopup = _page.addInterestedParty();
        interestedPartyPopup.selectWellKnownFinanceTypeAndOtherInterestedParty();
        return _page;
    }

}
