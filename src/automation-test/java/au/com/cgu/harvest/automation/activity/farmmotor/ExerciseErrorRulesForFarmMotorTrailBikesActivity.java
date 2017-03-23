package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;

@Activity( "Exercising Error Rules for Farm Motor Trail Bikes" )
public class ExerciseErrorRulesForFarmMotorTrailBikesActivity extends
    AbstractActivity< FarmMotorTrailBikesPage >
{
    private HarvestPage _page;

    public ExerciseErrorRulesForFarmMotorTrailBikesActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailBikesPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailBikes();

        return vehicleDetailPage;

    }

}
