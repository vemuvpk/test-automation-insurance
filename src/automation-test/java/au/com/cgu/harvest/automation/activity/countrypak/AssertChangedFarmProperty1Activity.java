package au.com.cgu.harvest.automation.activity.countrypak;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Asert that the changed details in Farm Property Page are saved after suspend activity" )
public class AssertChangedFarmProperty1Activity extends AbstractActivity< FarmPropertyPage >
{

    private FarmPropertyPage _page;

    public AssertChangedFarmProperty1Activity( FarmPropertyPage page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyPage farmPropertyPage =
            _page.getNavigationTree().navigateToFarmProperty( 1 );
        assertEquals( "Listed Events", _page.getCoverTypeValue() );
        assertEquals( "500.000", _page.getExcessValue() );
        assertEquals( "$100", _page.getUnspecifiedFarmBuildingsValue() );
        assertEquals( "$5,000", _page.getAboveGroundFarmImprovementsValue() );
        assertEquals( "$500", _page.getAllFarmContentsLimitValue() );
        assertEquals( "$500", _page.getInAnyOneBuildingValue() );
        _page.getNavigationTree().getSection( SectionType.FARM_PROPERTY, 1 ).isTaken();

        return farmPropertyPage;

    }

}
