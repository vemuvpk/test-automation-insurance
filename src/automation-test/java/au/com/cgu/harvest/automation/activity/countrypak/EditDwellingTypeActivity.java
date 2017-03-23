package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.DwellingPopUp;

@Activity( "Edit Dwelling Type inDomestic Buildings and cancel the popup Activity" )
public class EditDwellingTypeActivity extends AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DomesticBuildingAndContentsPage _page;
    private int _rowNumber;

    public EditDwellingTypeActivity(
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage, int rowNumber )
    {
        _page = domesticBuildingAndContentsPage;
        _rowNumber = rowNumber;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DwellingPopUp dwellingPopup =
            _page.getDwellingGrid().getDwellingAtRow( _rowNumber ).edit();
        dwellingPopup.setDwellingType( "" );
        dwellingPopup.cancel();

        return new DomesticBuildingAndContentsPage( getWebDriver() );
    }
}
