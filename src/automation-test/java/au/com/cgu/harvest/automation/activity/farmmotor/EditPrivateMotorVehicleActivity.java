package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;

@Activity( "Edit an existing private motor vehicle" )
public class EditPrivateMotorVehicleActivity extends AbstractActivity< PrivateMotorVehiclePage >
{
    private HarvestPage _page;
    private int _positionInTree;

    public EditPrivateMotorVehicleActivity( HarvestPage page, int positionInTree )
    {
        _page = page;
        _positionInTree = positionInTree;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        PrivateMotorVehiclePage vehiclePage =
            _page.getNavigationTree().navigateToPrivateMotorVehicle( _positionInTree );
        return vehiclePage;
    }
}
