package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.farmmotor.IPageWithInterestedParties;

@Activity( "Assert For Interested Party In Second Vehicle And Delete Interested Party Activity" )
public class AssertForInterestedpartyInSecondVehicleAndDeleteInterestedPartyActivity< PAGE extends IPageWithInterestedParties >
    extends
    AbstractActivity< PAGE >
{
    private PAGE _page;

    public AssertForInterestedpartyInSecondVehicleAndDeleteInterestedPartyActivity(
        PAGE vehicleDetailPage )
    {
        _page = vehicleDetailPage;
        _page.setVehicleIsFinanced( "true" );
    }

    @Override
    protected PAGE execute()
    {
        _page
            .getInterestedPartyGrid()
            .getInterestedPartyAtRow( 2 )
            .hasValues( false, "Hire Purchase", "Vemu Finance", "59-61 Good Street SYDNEY NSW 2000" );
        AbstractScenario.getScenarioLogger()
            .trace( "Interested party found in the second Vehicles" );
        _page.getInterestedPartyGrid().getInterestedPartyAtRow( 2 )
            .deleteInterestedParty( true );
        _page.getInterestedPartyGrid().getInterestedPartyAtRow( 1 ).setInterestedParty();
        return _page;
    }

}
