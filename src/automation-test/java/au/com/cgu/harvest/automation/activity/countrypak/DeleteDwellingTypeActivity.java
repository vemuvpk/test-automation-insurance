package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;

@Activity( "Delete Dwelling Type in Domestic Building page Activity" )
public class DeleteDwellingTypeActivity extends AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DomesticBuildingAndContentsPage _page;
    private int _rowNumber;

    public DeleteDwellingTypeActivity(
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage, int rowNumber )
    {
        _page = domesticBuildingAndContentsPage;
        _rowNumber = rowNumber;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            _page.getDwellingGrid().getDwellingAtRow( _rowNumber ).deleteDwellingType( true );

        return new DomesticBuildingAndContentsPage( getWebDriver() );
    }
}
