package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyBuildingTypePopUp;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Edit BuildingType in Farm Property Activity" )
public class EditFarmBuildingAndChangeBuildingTypeAndSumInsuredActivity extends
    AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyPage _page;
    private int _rowNumber;

    public EditFarmBuildingAndChangeBuildingTypeAndSumInsuredActivity(
        FarmPropertyPage farmPropertyPage, int rowNumber )
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
        farmBuildingPopup.setBuildingType( "Cool Room" );
        farmBuildingPopup.setBuildingSumInsured( "5000" );
        farmBuildingPopup.setContentsSumInsured( "5000" );
        farmBuildingPopup.ok();

        return new FarmPropertyPage( getWebDriver() );
    }
}
