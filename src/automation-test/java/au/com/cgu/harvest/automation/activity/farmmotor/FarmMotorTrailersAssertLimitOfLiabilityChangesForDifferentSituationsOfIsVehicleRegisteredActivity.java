package au.com.cgu.harvest.automation.activity.farmmotor;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;

@Activity( "FarmMotor Trailers Assert LimitOfLiability Changes For Different Situations Of IsVehicleRegistered Activity" )
public class FarmMotorTrailersAssertLimitOfLiabilityChangesForDifferentSituationsOfIsVehicleRegisteredActivity
    extends
    AbstractActivity< FarmMotorTrailerPage >
{
    private HarvestPage _page;

    public FarmMotorTrailersAssertLimitOfLiabilityChangesForDifferentSituationsOfIsVehicleRegisteredActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailerPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailerPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailer();

        // assertLimitOfLiabilityValueForDifferentIsVehicleRegistered()
        vehicleDetailPage.setIsRegistered( "true" );
        vehicleDetailPage.setRegistrationNumber( "VEMU-06" );
        Assert.assertEquals( "30000000.000", vehicleDetailPage.getLimitOfLiability() );
        AbstractScenario.getScenarioLogger().trace(
            "Limit of Liability is $30,000 for registered vehicle" );

        vehicleDetailPage.setIsRegistered( "false" );
        Assert.assertEquals( "0.000", vehicleDetailPage.getLimitOfLiability() );
        AbstractScenario.getScenarioLogger().trace(
            "Limit of Liability is Not Insured for NON-Registered vehicle" );

        return vehicleDetailPage;

    }

}
