package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.DwellingPopUp;

@Activity( "Add DwellingType to Domestic Building Activity" )
public class AddDwellingTypeToSecondSituationActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DomesticBuildingAndContentsPage _page;

    public AddDwellingTypeToSecondSituationActivity(
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage )
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
        dwelllingPopUp.ok();
        return _page;
    }
}
