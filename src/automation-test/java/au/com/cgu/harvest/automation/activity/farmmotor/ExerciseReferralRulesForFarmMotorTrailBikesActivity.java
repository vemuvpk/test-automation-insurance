package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;

@Activity( "Exercising Referral Rules for Farm Motor Trail Bikes" )
public class ExerciseReferralRulesForFarmMotorTrailBikesActivity extends
    AbstractActivity< FarmMotorTrailBikesPage >
{
    private HarvestPage _page;

    public ExerciseReferralRulesForFarmMotorTrailBikesActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailBikesPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailBikes();

        vehicleDetailPage.exerciseReferralRules();

        return vehicleDetailPage;

    }
}
