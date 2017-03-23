package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.DwellingPopUp;

@Activity( "Add Second Dwelling Type to Domestic Building Activity" )
public class AddSecondDwellingType extends AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DomesticBuildingAndContentsPage _page;

    public AddSecondDwellingType( DomesticBuildingAndContentsPage domesticBuildingAndContentsPage )
    {
        _page = domesticBuildingAndContentsPage;
        _page.addDwellingType();
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DwellingPopUp dwelllingPopUp = _page.addDwellingType();

        dwelllingPopUp.setDwellingType( "Caravan" );
        dwelllingPopUp.setAdditionalDwellingDetails( "Additional Details" );
        dwelllingPopUp.setYearBuilt( "2011" );
        dwelllingPopUp.setPropertyOccupancy( "Owner Occupied" );
        dwelllingPopUp.setOccupancyStatus( "Permanently Occupied" );
        dwelllingPopUp.setConstructionType( "Brick/Concrete Floors" );
        dwelllingPopUp.setIsBuildingHistorical( "false" );
        dwelllingPopUp.setIsBuildingUnderRennovations( "No" );
        dwelllingPopUp.setBuildingSumInsured( "5000" );
        dwelllingPopUp.setContentsSumInsured( "3000" );
        dwelllingPopUp.ok();
        return _page;
    }
}
