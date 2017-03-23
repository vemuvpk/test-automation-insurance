package au.com.cgu.harvest.automation.activity.farmmotor;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.RetryingElementLocator;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePageXpath;

@Activity( "HeavyCommercialVehicle Assert LimitOfLiability When Vehicle Is Not Registered Activity" )
public class HeavyCommercialVehicleAssertLimitOfLiabilityWhenVehicleIsNotRegisteredActivity
    extends
    AbstractActivity< HeavyCommercialVehiclePage >
{
    private HarvestPage _page;

    public HeavyCommercialVehicleAssertLimitOfLiabilityWhenVehicleIsNotRegisteredActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HeavyCommercialVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        HeavyCommercialVehiclePage vehicleDetailPage = addVehiclePage.addVehicleOver2Tonnes();

        // assertLimitOfLiability
        RetryingElementLocator.getListBox( getWebDriver(), "Is vehicle registered",
            HeavyCommercialVehiclePageXpath.IS_VEHICLE_REGISTERED,
            "false" );
        Assert.assertEquals( "0.000", vehicleDetailPage.getLimitOfLiability() );
        return vehicleDetailPage;

    }
}
