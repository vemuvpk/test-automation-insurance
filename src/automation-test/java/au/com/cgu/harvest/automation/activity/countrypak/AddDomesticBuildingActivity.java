package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add Domestic Building Activity" )
public class AddDomesticBuildingActivity extends AbstractActivity< DomesticBuildingAndContentsPage >
{
    private HarvestPage _page;

    public AddDomesticBuildingActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            _page.getNavigationTree().navigateToDomesticBuilding( 1 );

        domesticBuildingAndContentsPage.setCoverType( "Listed Events" );
        domesticBuildingAndContentsPage.setExcess( "500.000" );
        domesticBuildingAndContentsPage.setAdditionalBusinessContentsSumInsured( "100" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.DOMESTIC_BUILDING, 1 );
        section.isTaken();

        return domesticBuildingAndContentsPage;
    }
}
