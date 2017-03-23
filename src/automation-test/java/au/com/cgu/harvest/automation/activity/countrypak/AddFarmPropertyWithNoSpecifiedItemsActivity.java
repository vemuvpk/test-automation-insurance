package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Add Farm Property to a Situation With no specified Items Activity(Usually for Second Situation - as Specified items is not present in second situation)" )
public class AddFarmPropertyWithNoSpecifiedItemsActivity extends
    AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyPage _page;

    public AddFarmPropertyWithNoSpecifiedItemsActivity( FarmPropertyPage page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        _page.setCoverType( "Listed Events" );
        _page.setExcess( "500.000" );
        _page.setUnspecifiedFarmBuildings( "100" );
        _page.setAllFarmContentsLimit( "500" );
        _page.setInAnyOneBuilding( "500" );
        _page.setAboveGroundFarmImprovements( "5000" );
        _page.getSpecifiedAboveGroundFarmImprovementsGrid().addRow( "Description-1",
            "5000" );
        _page.getSpecifiedAboveGroundFarmImprovementsGrid().addRow( "Description-2",
            "5000" );

        return _page;
    }
}
