package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.DwellingPopUp;

@Activity( "Add DwellingType to Domestic Building Activity" )
public class AddDwellingType extends AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DomesticBuildingAndContentsPage _page;

    public AddDwellingType( DomesticBuildingAndContentsPage domesticBuildingAndContentsPage )
    {
        _page = domesticBuildingAndContentsPage;
        _page.addDwellingType();
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DwellingPopUp dwelllingPopUp = _page.addDwellingType();

        dwelllingPopUp.setDwellingType( "Dwelling" );
        dwelllingPopUp.setAdditionalDwellingDetails( "Additional Details" );
        dwelllingPopUp.setYearBuilt( "2010" );
        dwelllingPopUp.setPropertyOccupancy( "Owner Occupied" );
        dwelllingPopUp.setOccupancyStatus( "Permanently Occupied" );
        dwelllingPopUp.setConstructionType( "Brick/Concrete Floors" );
        dwelllingPopUp.setIsBuildingHistorical( "false" );
        dwelllingPopUp.setIsBuildingUnderRennovations( "No" );
        dwelllingPopUp.setBuildingSumInsured( "500" );
        dwelllingPopUp.setContentsSumInsured( "5000" );
        dwelllingPopUp.getSpecifiedContentsGrid().addRow( "Contents-1", "100" );
        dwelllingPopUp.getSpecifiedContentsGrid().addRow( "Contents-2", "200" );
        dwelllingPopUp.ok();
        return _page;
    }
}
