package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Navigate to Farm Property Page in Countrypak" )
public class NavigateToFarmPropertyPageActivity extends
    AbstractActivity< FarmPropertyPage >

{
    private HarvestPage _page;
    private int _situation;

    public NavigateToFarmPropertyPageActivity( HarvestPage page, int situation )
    {
        _page = page;
        _situation = situation;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyPage farmPropertyPage =
            _page.getNavigationTree().navigateToFarmProperty( _situation );
        return farmPropertyPage;
    }
}
