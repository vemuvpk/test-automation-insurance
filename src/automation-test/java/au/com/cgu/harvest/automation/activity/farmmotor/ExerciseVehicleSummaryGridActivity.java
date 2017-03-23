package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryGridRow;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPageGrid;

@Activity( "Exercise the Vehicle Summary grid by expanding and collapsing rows and checking Values" )
public class ExerciseVehicleSummaryGridActivity extends AbstractActivity< VehicleSummaryPage >
{
    private HarvestPage _page;

    public ExerciseVehicleSummaryGridActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected VehicleSummaryPage execute()
    {
        VehicleSummaryPage vehicleSummaryPage =
            _page.getNavigationTree().navigateToVehicleSummaryPage();
        // 1. make sure there is a row for each vehicle and the rows are collapsed
        VehicleSummaryPageGrid vehicleSummaryPageGrid =
            vehicleSummaryPage.getVehicleSummaryPageGrid();
        assertEquals( "Should have a row for each vehicle", 7,
            vehicleSummaryPageGrid.getNumberOfRows() );

        // 2. Each vehicle has 1 child cover row
        VehicleSummaryGridRow row = vehicleSummaryPageGrid.getVehicleAtRow( 1 );
        row.hasValues( "Private Motor Vehicle", "" );
        assertEquals( 1, row.getChildRowCount() );

        VehicleSummaryGridRow childRow = row.getChildRow( 1 );
        childRow.hasValues( "AAA-999 HOLDEN BARINA", "Comprehensive, Windscreen" );

        // Check Vehicle Summary for 2nd Vehicle
        vehicleSummaryPageGrid.expandAll();
        row = vehicleSummaryPageGrid.getVehicleAtRow( 2 );
        row.hasValues( "Trailers - Luggage/Boat/Box/Horse", "" );
        assertEquals( 1, row.getChildRowCount() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "AAA-998 Description Vehicle", "Comprehensive" );

        // Check Vehicle Summary for 3rd Vehicle
        vehicleSummaryPageGrid.expandAll();
        row = vehicleSummaryPageGrid.getVehicleAtRow( 3 );
        row.hasValues( "Goods Carrying vehicle up to 2 tonnes", "" );
        assertEquals( 1, row.getChildRowCount() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "REGO AUDI A6", "Comprehensive, Windscreen" );

        // Check Vehicle Summary for 4th Vehicle
        vehicleSummaryPageGrid.expandAll();
        row = vehicleSummaryPageGrid.getVehicleAtRow( 4 );
        row.hasValues( "Vehicles over 2 and up to 5 tonnes", "" );
        assertEquals( 1, row.getChildRowCount() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "REGO FIAT DUCATO", "Comprehensive" );

        // Check Vehicle Summary for 5th Vehicle
        vehicleSummaryPageGrid.expandAll();
        row = vehicleSummaryPageGrid.getVehicleAtRow( 5 );
        row.hasValues( "Non-articulated trailers", "" );
        assertEquals( 1, row.getChildRowCount() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "VEMU Jinker Trailer", "Comprehensive" );

        // Check Vehicle Summary for 6th Vehicle
        vehicleSummaryPageGrid.expandAll();
        row = vehicleSummaryPageGrid.getVehicleAtRow( 6 );
        row.hasValues( "Motorised machinery", "" );
        assertEquals( 1, row.getChildRowCount() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "AAA-998 Caterpillar XSD", "Comprehensive" );

        // Check Vehicle Summary for 7th Vehicle
        vehicleSummaryPageGrid.expandAll();
        row = vehicleSummaryPageGrid.getVehicleAtRow( 7 );
        row.hasValues( "Trail Bikes (Farm/Agriculture Use)", "" );
        assertEquals( 1, row.getChildRowCount() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "VE06MU SUZUKI 70CC", "Comprehensive" );

        return vehicleSummaryPage;
    }
}
