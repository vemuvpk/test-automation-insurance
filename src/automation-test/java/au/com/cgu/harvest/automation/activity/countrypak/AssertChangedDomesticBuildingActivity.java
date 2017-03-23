package au.com.cgu.harvest.automation.activity.countrypak;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Asert that the changed details in DomesticBuildingAndContentsPage are saved after suspend activity" )
public class AssertChangedDomesticBuildingActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{

    private DomesticBuildingAndContentsPage _page;

    public AssertChangedDomesticBuildingActivity( DomesticBuildingAndContentsPage page )
    {
        _page = page;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            _page.getNavigationTree().navigateToDomesticBuilding( 1 );
        assertEquals( "Listed Events", _page.getCoverTypeValue() );
        assertEquals( "500.000", _page.getExcessValue() );
        assertEquals( "$100", _page.getAdditionalBusinessContentsSumInsuredValue() );
        _page.getNavigationTree().getSection( SectionType.DOMESTIC_BUILDING, 1 ).isTaken();

        return domesticBuildingAndContentsPage;

    }
}
