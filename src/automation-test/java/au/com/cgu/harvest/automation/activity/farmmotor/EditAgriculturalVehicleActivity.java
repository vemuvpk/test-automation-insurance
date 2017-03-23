package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;

@Activity( "Edit an existing Agricultural motor vehicle" )
public class EditAgriculturalVehicleActivity extends AbstractActivity< AgriculturalVehiclePage >
{
    private HarvestPage _page;
    private int _positionInTree;

    public EditAgriculturalVehicleActivity( HarvestPage page, int positionInTree )
    {
        _page = page;
        _positionInTree = positionInTree;
    }

    @Override
    protected AgriculturalVehiclePage execute()
    {
        AgriculturalVehiclePage vehiclePage =
            _page.getNavigationTree().navigateToAgriculturalVehicle( _positionInTree );
        return vehiclePage;
    }
}
