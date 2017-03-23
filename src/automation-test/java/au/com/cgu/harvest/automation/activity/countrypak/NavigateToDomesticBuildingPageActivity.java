package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;

@Activity( "Navigate to Domestic Building and Contents Page in Countrypak" )
public class NavigateToDomesticBuildingPageActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >

{
    private HarvestPage _page;
    private int _situation;

    public NavigateToDomesticBuildingPageActivity( HarvestPage page, int situation )
    {
        _page = page;
        _situation = situation;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            _page.getNavigationTree().navigateToDomesticBuilding( _situation );
        return domesticBuildingAndContentsPage;
    }
}
