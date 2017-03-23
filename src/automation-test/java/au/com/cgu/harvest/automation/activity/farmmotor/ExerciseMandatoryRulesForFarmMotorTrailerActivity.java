package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;

@Activity( "Exercising Mandatory Rules for Farm Motor Trailers" )
public class ExerciseMandatoryRulesForFarmMotorTrailerActivity extends
    AbstractActivity< FarmMotorTrailerPage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForFarmMotorTrailerActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailerPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailerPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailer();
        vehicleDetailPage.exerciseMandatoryRules();

        return vehicleDetailPage;

    }

}
