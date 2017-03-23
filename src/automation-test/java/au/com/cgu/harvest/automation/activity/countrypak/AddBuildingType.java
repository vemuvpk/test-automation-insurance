package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyBuildingTypePopUp;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Add BuildingType to Farm Property Activity" )
public class AddBuildingType extends AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyPage _page;

    public AddBuildingType( FarmPropertyPage farmPropertyPage )
    {
        _page = farmPropertyPage;
        _page.addBuildingType();
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyBuildingTypePopUp buildingTypePopUp = _page.addBuildingType();

        buildingTypePopUp.setBuildingType( "Cool Room" );
        buildingTypePopUp.setIsPropertyUnderRennovations( "No" );
        buildingTypePopUp.setConstructionType( "Brick/Concrete Floors" );
        buildingTypePopUp.setYearOfConstruction( "2010" );
        buildingTypePopUp.setReplacement( "false" );
        buildingTypePopUp.setBuildingSumInsured( "500" );
        buildingTypePopUp.setContentsSumInsured( "5000" );
        buildingTypePopUp.ok();
        return _page;
    }
}
