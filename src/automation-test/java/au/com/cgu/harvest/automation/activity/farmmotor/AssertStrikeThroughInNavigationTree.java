package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.NavigationTree;
import au.com.cgu.harvest.pages.farmmotor.VehicleSummaryPage;

@Activity( "Assert strike through/Deleted to the vehicle in the Navigation tree" )
public class AssertStrikeThroughInNavigationTree extends AbstractActivity< VehicleSummaryPage >
{
    private HarvestPage _page;
    private int _positionInTree;

    public AssertStrikeThroughInNavigationTree( HarvestPage page, int positionInTree )
    {
        _page = page;
        _positionInTree = positionInTree;
    }

    @Override
    protected VehicleSummaryPage execute()
    {
        NavigationTree navigationTree =
            _page.getNavigationTree();
        navigationTree.navigateToAgriculturalVehicle( _positionInTree );
        // String element =
        // navigationTree.NAVTREE_STRIKETHROUGH_LOCATOR;

        return new VehicleSummaryPage( getWebDriver() );
    }
}
