package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePage;

@Activity( "Exercise Referral Rules for Heavy Commercial Vehicle activity" )
public class ExerciseReferralRulesForHeavyCommercialVehicleActivity extends
    AbstractActivity< HeavyCommercialVehiclePage >
{
    private HarvestPage _page;

    public ExerciseReferralRulesForHeavyCommercialVehicleActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HeavyCommercialVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        HeavyCommercialVehiclePage vehicleDetailPage = addVehiclePage.addVehicleOver2Tonnes();

        vehicleDetailPage.exerciseReferralRules();

        return vehicleDetailPage;

    }

}
