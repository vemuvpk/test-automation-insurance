package au.com.cgu.harvest.automation.activity.farmmotor;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;

@Activity( "Assert LimitOfLiability Changes For Different Situations of IsVehicleRegisteredActivity" )
public class TrailBikesAssertLimitOfLiabilityChangesForDifferentSituationsofIsVehicleRegisteredActivity
    extends
    AbstractActivity< FarmMotorTrailBikesPage >
{
    private HarvestPage _page;

    public TrailBikesAssertLimitOfLiabilityChangesForDifferentSituationsofIsVehicleRegisteredActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailBikesPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailBikes();

        // Assert LimitOfLiabilityValue ForDifferent "IsVehicleRegistered" situations
        vehicleDetailPage.setIsRegistered( "true" );
        vehicleDetailPage.setRegistrationNumber( "VEMU-06" );
        Assert.assertEquals( "$30,000,000", vehicleDetailPage.getLimitOfLiability() );
        AbstractScenario.getScenarioLogger().trace(
            "Limit of Liability is $30,000 for registered vehicle" );
        vehicleDetailPage.setIsRegistered( "false" );
        Assert.assertEquals( "Not Insured", vehicleDetailPage.getLimitOfLiability() );
        AbstractScenario.getScenarioLogger().trace(
            "Limit of Liability is Not Insured for NON-Registered vehicle" );

        return vehicleDetailPage;

    }

}
