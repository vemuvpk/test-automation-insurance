package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.IPageWithInterestedParties;
import au.com.cgu.harvest.pages.farmmotor.InterestedPartyRow;
import au.com.cgu.harvest.pages.farmmotor.InterestedPartySelectionPopup;

@Activity( "Exercise Interested Party Grid Activity" )
public class ExerciseInterestedPartyGridActivity< PAGE extends IPageWithInterestedParties >
    extends AbstractActivity< PAGE >
{
    private PAGE _page;

    public ExerciseInterestedPartyGridActivity( PAGE vehicleDetailPage )
    {
        _page = vehicleDetailPage;
    }

    @Override
    protected PAGE execute()
    {
        // Popup is displayed if no interested parties on policy AND is financed is "yes"
        _page.setVehicleIsFinanced( "true" );
        InterestedPartySelectionPopup interestedPartyPopup = _page.addInterestedParty();
        interestedPartyPopup.setWellKnownFinanceTypeWellKnownInterestedParty();

        // check for mandatory fields in the IP popup
        _page.addInterestedParty().checkForMandatoryFieldErrors();
        _page.addInterestedParty().selectOtherFinanceTypeAndOtherInterestedParty();
        _page.addInterestedParty().selectWellKnownFinanceTypeAndOtherInterestedParty();

        // edit one of the IP and change the details
        interestedPartyPopup =
            _page.getInterestedPartyGrid().getInterestedPartyAtRow( 2 ).edit();
        interestedPartyPopup.selectInterestedParty( "OTHER_LEASE", "CBFCM" );

        // delete an IP
        _page.getInterestedPartyGrid().getInterestedPartyAtRow( 2 )
            .deleteInterestedParty( true );
        _page.getInterestedPartyGrid().getInterestedPartyAtRow( 2 )
            .hasValues( true, "Hire Purchase", "Vemu Finance",
                "59-61 Good Street SYDNEY NSW 2000" );

        // select a different IP as default
        InterestedPartyRow interestedPartyRow =
            _page.getInterestedPartyGrid().getInterestedPartyAtRow( 1 );
        interestedPartyRow.setInterestedParty();
        _page.getInterestedPartyGrid().getInterestedPartyAtRow( 2 ).setInterestedParty();
        return _page;
    }

}
