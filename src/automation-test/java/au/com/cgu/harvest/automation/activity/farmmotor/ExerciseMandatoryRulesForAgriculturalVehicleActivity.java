package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;

@Activity( "Exercise Mandatory Rules for Agricultural Vehicle activity" )
public class ExerciseMandatoryRulesForAgriculturalVehicleActivity extends
    AbstractActivity< AgriculturalVehiclePage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForAgriculturalVehicleActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected AgriculturalVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        AgriculturalVehiclePage vehicleDetailPage = addVehiclePage.addAgriculturalMotorVehicle();
        vehicleDetailPage.exerciseMandatoryFields();

        return vehicleDetailPage;

    }
}
