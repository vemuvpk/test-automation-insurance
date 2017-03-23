package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.VehicleUnder2TonnesPage;

@Activity( "Exercise Mandatory Rules for Vehicle Under 2 Tonnes activity" )
public class ExerciseMandatoryRulesForVehicleUnder2TonnesActivity extends
    AbstractActivity< VehicleUnder2TonnesPage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForVehicleUnder2TonnesActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected VehicleUnder2TonnesPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        VehicleUnder2TonnesPage vehicleDetailPage = addVehiclePage.addVehicleUnder2Tonnes();
        vehicleDetailPage.exerciseMandatoryFieldsForErrors();

        return vehicleDetailPage;

    }
}
