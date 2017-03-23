package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumRow;

@Activity( "Exercise the premium grid by expanding and collapsing rows and checking totals" )
public class ExercisePremiumGridActivity extends AbstractActivity< PremiumPage >
{
    private HarvestPage _page;

    public ExercisePremiumGridActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PremiumPage execute()
    {
        PremiumPage premiumPage = _page.getNavigationTree().navigateToTransactionPremium();
        // 1. make sure there is a row for each vehicle and the rows are collapsed

        assertEquals( "Should have a row for each vehicle", 7, premiumPage.getPremiumGrid()
            .getNumberOfRows() );

        // 2. Each vehicle has 1 child cover row
        PremiumRow row = premiumPage.getPremiumGrid().getPremiumAtRow( 1 );
        row.hasValues( "AAA-999 HOLDEN BARINA", "$968.42", "$9.68", "$97.81", "$53.80",
            "", "$1,129.71" );
        assertEquals( 2, row.getChildRowCount() );
        assertTrue( "Row should be collapsed", row.isCollapsed() );
        row.toggleExpanded();
        assertTrue( "Row should be expanded", row.isExpanded() );

        PremiumRow childRow = row.getChildRow( 1 );
        childRow.hasValues( "Comprehensive", "$938.42", "$9.38", "$94.78", "$52.13",
            "", "$1,094.71" );

        childRow = row.getChildRow( 2 );
        childRow.hasValues( "Windscreen", "$30.00", "$0.30", "$3.03", "$1.67",
            "", "$35.00" );
        // Check Premium for 2nd Vehicle
        premiumPage.getPremiumGrid().collapseAll();
        row = premiumPage.getPremiumGrid().getPremiumAtRow( 2 );
        row.hasValues( "AAA-998 Caterpillar XSD", "$450.55", "$4.51", "$45.51", "$25.03", "",
            "$525.60" );
        assertEquals( 1, row.getChildRowCount() );
        assertTrue( "Row should be collapsed", row.isCollapsed() );
        row.toggleExpanded();
        assertTrue( "Row should be expanded", row.isExpanded() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "Comprehensive", "$450.55", "$4.51", "$45.51", "$25.03", "",
            "$525.60" );

        // Check Premium for 3rd Vehicle
        premiumPage.getPremiumGrid().collapseAll();
        row = premiumPage.getPremiumGrid().getPremiumAtRow( 3 );
        row.hasValues( "REGO FIAT DUCATO", "$476.90", "$4.77", "$48.17", "$26.49", "",
            "$556.33" );
        assertEquals( 1, row.getChildRowCount() );
        assertTrue( "Row should be collapsed", row.isCollapsed() );
        row.toggleExpanded();
        assertTrue( "Row should be expanded", row.isExpanded() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "Comprehensive", "$476.90", "$4.77", "$48.17", "$26.49", "",
            "$556.33" );

        // Check Premium for 4th Vehicle
        premiumPage.getPremiumGrid().collapseAll();
        row = premiumPage.getPremiumGrid().getPremiumAtRow( 4 );
        row.hasValues( "REGO AUDI A6", "$2,552.17", "$25.52", "$257.77", "$141.78", "",
            "$2,977.24" );
        assertEquals( 2, row.getChildRowCount() );
        assertTrue( "Row should be collapsed", row.isCollapsed() );
        row.toggleExpanded();
        assertTrue( "Row should be expanded", row.isExpanded() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "Comprehensive", "$2,522.17", "$25.22", "$254.74", "$140.11", "",
            "$2,942.24" );
        childRow = row.getChildRow( 2 );
        childRow.hasValues( "Windscreen", "$30.00", "$0.30", "$3.03", "$1.67", "",
            "$35.00" );

        // Check Premium for 5th Vehicle
        premiumPage.getPremiumGrid().collapseAll();
        row = premiumPage.getPremiumGrid().getPremiumAtRow( 5 );
        row.hasValues( "AAA-998 Description Vehicle", "$493.50", "$4.94", "$49.84", "$27.41",
            "", "$575.69" );
        assertEquals( 1, row.getChildRowCount() );
        assertTrue( "Row should be collapsed", row.isCollapsed() );
        row.toggleExpanded();
        assertTrue( "Row should be expanded", row.isExpanded() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "Comprehensive", "$493.50", "$4.94", "$49.84", "$27.41", "",
            "$575.69" );

        // Check Premium for 6th Vehicle
        premiumPage.getPremiumGrid().collapseAll();
        row = premiumPage.getPremiumGrid().getPremiumAtRow( 6 );
        row.hasValues( "VE06MU SUZUKI 70CC", "$287.00", "$2.87", "$28.99", "$15.94", "",
            "$334.80" );
        assertEquals( 1, row.getChildRowCount() );
        assertTrue( "Row should be collapsed", row.isCollapsed() );
        row.toggleExpanded();
        assertTrue( "Row should be expanded", row.isExpanded() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "Comprehensive", "$287.00", "$2.87", "$28.99", "$15.94", "",
            "$334.80" );

        // Check Premium for 7th Vehicle
        premiumPage.getPremiumGrid().collapseAll();
        row = premiumPage.getPremiumGrid().getPremiumAtRow( 7 );
        row.hasValues( "VEMU", "$56.88", "$0.57", "$5.74", "$3.16", "",
            "$66.35" );
        assertEquals( 1, row.getChildRowCount() );
        assertTrue( "Row should be collapsed", row.isCollapsed() );
        row.toggleExpanded();
        assertTrue( "Row should be expanded", row.isExpanded() );

        childRow = row.getChildRow( 1 );
        childRow.hasValues( "VEMU", "$56.88", "$0.57", "$5.74", "$3.16", "",
            "$66.35" );
        // 3. Totals
        row = premiumPage.getPremiumGrid().getFooterRow();
        assertEquals( "Policy Total", row.getRisk() );
        assertEquals( "$5,285.42", row.getTechnicalPremium() );
        assertEquals( "$52.86", row.getFireLevy() );
        assertEquals( "$533.83", row.getGst() );
        assertEquals( "$293.61", row.getStampDuty() );
        assertEquals( "0%", row.getAdjustment() );
        assertEquals( "$6,165.72", row.getTotalPremium() );

        premiumPage.getPremiumGrid().collapseAll();
        premiumPage.getPremiumGrid().expandAll();

        return premiumPage;
    }
}
