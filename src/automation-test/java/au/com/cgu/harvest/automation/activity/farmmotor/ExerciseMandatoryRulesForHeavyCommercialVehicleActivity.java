package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePage;

@Activity( "Exercise Mandatory Rules for Heavy Commercial Vehicle activity" )
public class ExerciseMandatoryRulesForHeavyCommercialVehicleActivity extends
    AbstractActivity< HeavyCommercialVehiclePage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForHeavyCommercialVehicleActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HeavyCommercialVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        HeavyCommercialVehiclePage vehicleDetailPage = addVehiclePage.addVehicleOver2Tonnes();
        vehicleDetailPage.exerciseMandatoryFieldsForErrors();

        return vehicleDetailPage;

    }
}
