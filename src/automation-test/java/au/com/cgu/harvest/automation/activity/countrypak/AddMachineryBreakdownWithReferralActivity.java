package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.countrypak.NameValueGridRow;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add Business Liability Page With all fields entered Activity" )
public class AddMachineryBreakdownWithReferralActivity extends
    AbstractActivity< MachineryBreakdownPage >
{
    private HarvestPage _page;

    public AddMachineryBreakdownWithReferralActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected MachineryBreakdownPage execute()
    {
        MachineryBreakdownPage machineryBreakdownPage =
            _page.getNavigationTree().navigateToMachineryBreakdown();

        machineryBreakdownPage.setBlanketCover( "true" );
        machineryBreakdownPage.setBlanketCoverRatingUnits( "2" );
        machineryBreakdownPage.setSpecifiedItemsRatingUnits( "3" );

        NameValueGridRow specifiedItemRow =
            machineryBreakdownPage.getSpecifiedItemsGrid().addRow();
        specifiedItemRow.setValues( "Description Name", "20500" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.MACHINERY_BREAKDOWN );
        section.isTaken();

        return machineryBreakdownPage;
    }
}
