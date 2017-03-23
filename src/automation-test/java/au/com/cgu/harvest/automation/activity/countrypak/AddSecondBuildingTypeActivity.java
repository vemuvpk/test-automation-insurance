package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyBuildingTypePopUp;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Add BuildingType to Farm Property Activity" )
public class AddSecondBuildingTypeActivity extends AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyPage _page;

    public AddSecondBuildingTypeActivity( FarmPropertyPage farmPropertyPage )
    {
        _page = farmPropertyPage;
        _page.addBuildingType();
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyBuildingTypePopUp buildingTypePopUp = _page.addBuildingType();

        buildingTypePopUp.setBuildingType( "Aircraft Hangar" );
        buildingTypePopUp.setIsPropertyUnderRennovations( "No" );
        buildingTypePopUp.setConstructionType( "Brick/Concrete Floors" );
        buildingTypePopUp.setYearOfConstruction( "2005" );
        buildingTypePopUp.setReplacement( "true" );
        buildingTypePopUp.setBuildingSumInsured( "1000" );
        buildingTypePopUp.setContentsSumInsured( "1000" );
        buildingTypePopUp.ok();
        return _page;
    }
}
