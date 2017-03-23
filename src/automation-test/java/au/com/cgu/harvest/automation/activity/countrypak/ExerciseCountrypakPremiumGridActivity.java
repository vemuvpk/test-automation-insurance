package au.com.cgu.harvest.automation.activity.countrypak;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumRow;

@Activity( "Exercise the Countrypak premium grid by expanding and collapsing rows and checking totals" )
public class ExerciseCountrypakPremiumGridActivity extends AbstractActivity< PremiumPage >
{
    private HarvestPage _page;

    public ExerciseCountrypakPremiumGridActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PremiumPage execute()
    {
        PremiumPage premiumPage = _page.getNavigationTree().navigateToCountrypakPremium();
        // 1. make sure there is a row for each Section and the rows are collapsed
        assertEquals( "Should have a row for each Section", 45, premiumPage.getPremiumGrid()
            .getNumberOfRows() );

        // Check each Parent Row for values
        PremiumRow row = premiumPage.getPremiumGrid().getPremiumAtRow( 1 );
        row.hasValues( "Situation - 1", "$822.89", "$214.22", "$103.69", "$102.55", "", "$1,243.35" );

        row = premiumPage.getPremiumGrid().getPremiumAtRow( 21 );
        row.hasValues( "Situation-2", "$47.22", "$18.89", "$6.60", "$6.55", "", "$79.26" );

        row = premiumPage.getPremiumGrid().getPremiumAtRow( 24 );
        row.hasValues( "Farm Machinery And Working Dogs", "$365.33", "$146.13", "$51.14", "$50.63",
            "", "$613.23" );
        row = premiumPage.getPremiumGrid().getPremiumAtRow( 31 );
        row.hasValues( "Theft", "$183.85", "$0.00", "$18.39", "$18.21", "", "$220.45" );

        row = premiumPage.getPremiumGrid().getPremiumAtRow( 38 );
        row.hasValues( "Business Interruption", "$7.00", "$2.80", "$0.98", "$0.97", "", "$11.75"
            );
        row = premiumPage.getPremiumGrid().getPremiumAtRow( 39 );
        row.hasValues( "Business Liability", "$451.12", "$0.00", "$45.11", "$44.66", "", "$540.89" );

        row = premiumPage.getPremiumGrid().getPremiumAtRow( 40 );
        row.hasValues( "Machinery Breakdown", "$499.96", "$0.00", "$50.00", "$49.50", "", "$599.46"
            );
        row = premiumPage.getPremiumGrid().getPremiumAtRow( 43 );
        row.hasValues( "Personal Income [Test Name]", "$297.12", "$0.00", "$29.71", "$16.34", "",
            "$343.17" );
        row = premiumPage.getPremiumGrid().getPremiumAtRow( 44 );
        row.hasValues( "Personal Income [Second Person]", "$297.12", "$0.00", "$29.71", "$16.34",
            "", "$343.17" );

        row = premiumPage.getPremiumGrid().getPremiumAtRow( 45 );
        row.hasValues( "Road Transit", "$1,169.19", "$0.00", "$116.92", "$0.00", "", "$1,286.11" );

        // 2. Each Section has child cover rows
        // PremiumRow row = premiumGrid.getPremiumAtRow( 1 );
        // row.hasValues( "Situation - 1", "$36.51", "$0.00", "$0.00", "$0.00",
        // "", "$36.51" );
        // assertEquals( 3, row.getChildRowCount() );
        // assertTrue( "Row should be collapsed", row.isCollapsed() );
        // row.toggleExpanded();
        // assertTrue( "Row should be expanded", row.isExpanded() );
        //
        // PremiumRow childRow = row.getChildRow( 1 );
        // childRow.hasValues( "Hay, Fencing, Livestock and Farm Trees", "$28.13", "$0.00", "$0.00",
        // "$0.00", "", "$28.13" );
        //
        // childRow = row.getChildRow( 2 );
        // childRow.hasValues( "Boundary Not Shared", "$25.57", "$0.00", "$0.00", "$0.00",
        // "", "$25.57" );
        //
        // childRow = row.getChildRow( 3 );
        // childRow.hasValues( "Boundary Shared", "$2.56", "$0.00", "$0.00", "$0.00",
        // "", "$2.56" );

        // 3. Totals
        row = premiumPage.getPremiumGrid().getFooterRow();
        assertEquals( "Policy Total", row.getRisk() );
        assertEquals( "$4,140.80", row.getTechnicalPremium() );
        assertEquals( "$382.04", row.getFireLevy() );
        assertEquals( "$452.25", row.getGst() );
        assertEquals( "$305.75", row.getStampDuty() );
        assertEquals( "$5,280.84", row.getTotalPremium() );

        premiumPage.getPremiumGrid().collapseAll();
        premiumPage.getPremiumGrid().expandAll();

        return premiumPage;
    }
}
