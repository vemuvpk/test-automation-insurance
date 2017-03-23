package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;

@Activity( "Exercise Referral Rules for PMV activity" )
public class ExerciseReferralRulesForPMVActivity extends
    AbstractActivity< PrivateMotorVehiclePage >
{
    private HarvestPage _page;

    public ExerciseReferralRulesForPMVActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        PrivateMotorVehiclePage vehicleDetailPage = addVehiclePage.addPrivateMotorVehicle();
        vehicleDetailPage.setVehicleWithExistingDamageForRules( "true", "R021" );
        vehicleDetailPage.setVehicleValueForRules( "551000", "Comprehensive",
            "D003" );
        vehicleDetailPage.setVehicleValueForRules( "151000", "Comprehensive",
            "R003" );
        vehicleDetailPage.setProofOfNumberOfAtFaultClaimFreeYears( "Comprehensive", "4 years",
            "No", "R020" );
        vehicleDetailPage.setGaragePostCodeForRules( "2000", "R008" );

        return vehicleDetailPage;

    }
}
