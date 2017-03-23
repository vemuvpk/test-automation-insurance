package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyBuildingTypePopUp;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Cancel farm property-Building Type Popup Activity" )
public class CancelBuildingTypePopupActivity extends
    AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyBuildingTypePopUp _page;

    public CancelBuildingTypePopupActivity( FarmPropertyBuildingTypePopUp page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyPage execute()
    {

        FarmPropertyPage farmPropertyPage =
            _page.cancel();
        return farmPropertyPage;
    }
}
