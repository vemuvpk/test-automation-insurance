package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;

@Activity( "Activity to Click on Next in Navigation Bar" )
public class NavigationBarClickNextActivity< PAGE extends HarvestPage > extends
    AbstractActivity< HarvestPage >
{
    private PAGE _page;

    public NavigationBarClickNextActivity( PAGE harvestPage )
    {
        _page = harvestPage;
    }

    @Override
    protected HarvestPage execute()
    {
        _page.getNavigationBar().clickNext();
        return _page;
    }
}
