package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.DwellingPopUp;

@Activity( "Cancel Domestic Building - Dwelling Type Popup Activity" )
public class CancelDwellingTypePopupActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DwellingPopUp _page;

    public CancelDwellingTypePopupActivity( DwellingPopUp page )
    {
        _page = page;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            _page.cancel();
        return domesticBuildingAndContentsPage;
    }
}
