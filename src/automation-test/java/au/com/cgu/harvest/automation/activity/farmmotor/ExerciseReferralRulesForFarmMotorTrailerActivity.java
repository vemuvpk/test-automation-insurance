package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;

@Activity( "Exercising Referral Rules for Farm Motor Trailers" )
public class ExerciseReferralRulesForFarmMotorTrailerActivity extends
    AbstractActivity< FarmMotorTrailerPage >
{
    private HarvestPage _page;

    public ExerciseReferralRulesForFarmMotorTrailerActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailerPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailerPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailer();

        vehicleDetailPage.exerciseReferralRules();

        return vehicleDetailPage;

    }

}
