package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyBuildingTypePopUp;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Edit BuildingType in Farm Property Activity" )
public class EditFarmBuildingActivity extends AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyPage _page;
    private int _rowNumber;

    public EditFarmBuildingActivity( FarmPropertyPage farmPropertyPage, int rowNumber )
    {
        _page = farmPropertyPage;
        _rowNumber = rowNumber;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyBuildingTypePopUp farmBuildingPopup =
            _page.getFarmPropertyBuildingTypeGrid().getFarmPropertyBuildingTypeAtRow( _rowNumber )
                .edit();
        farmBuildingPopup.setBuildingType( "" );
        farmBuildingPopup.cancel();

        return new FarmPropertyPage( getWebDriver() );
    }
}
