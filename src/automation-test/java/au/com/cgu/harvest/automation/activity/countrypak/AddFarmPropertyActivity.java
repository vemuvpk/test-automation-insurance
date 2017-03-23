package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelFarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add farm Property Activity" )
public class AddFarmPropertyActivity extends AbstractActivity< FarmPropertyPage >
{
    private HarvestPage _page;

    public AddFarmPropertyActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyPage farmPropertyPage =
            _page.getNavigationTree().navigateToFarmProperty( 1 );

        farmPropertyPage.setCoverType( "Listed Events" );
        farmPropertyPage.setExcess( "500.000" );
        farmPropertyPage.setUnspecifiedFarmBuildings( "100" );
        farmPropertyPage.setAllFarmContentsLimit( "500" );
        farmPropertyPage.setInAnyOneBuilding( "500" );
        farmPropertyPage.setAboveGroundFarmImprovements( "5000" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.FARM_PROPERTY, 1 );
        section.isTaken();
        farmPropertyPage.getSpecifiedAboveGroundFarmImprovementsGrid().addRow( "Description-1",
            "5000" );
        farmPropertyPage.getSpecifiedAboveGroundFarmImprovementsGrid().addRow( "Description-2",
            "5000" );
        PolicyLevelFarmPropertyPage policyLevelFarmProperty =
            _page.getNavigationTree().navigateToPolicyLevelFarmProperty();
        policyLevelFarmProperty.getSpecifiedItemsGrid().addRow( "Specified Items-1", "4000" );
        policyLevelFarmProperty.getSpecifiedItemsGrid().addRow( "Specified Items-2", "4000" );

        _page.getNavigationTree().navigateToFarmProperty( 1 );
        return farmPropertyPage;
    }
}
