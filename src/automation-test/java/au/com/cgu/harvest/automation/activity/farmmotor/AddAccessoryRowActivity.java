package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketAccessoriesGrid;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailBikesPage;

@Activity( "Add an Accessory Row to the Vehicle" )
public class AddAccessoryRowActivity extends
    AbstractActivity< FarmMotorTrailBikesPage >
{
    private FarmMotorTrailBikesPage _page;

    public AddAccessoryRowActivity( FarmMotorTrailBikesPage vehiclePage )
    {
        _page = vehiclePage;
    }

    @Override
    protected FarmMotorTrailBikesPage execute()
    {
        AfterMarketAccessoriesGrid accessories = _page.getAfterMarketAccessories();
        accessories.setHasAfterMarketAccessories( "true" );

        // AccessoryRow accessoryRow = accessories.getAccessoryAtRow( 1 );
        // accessoryRow.setValues( "Accessory Name 123", "500" );
        accessories.addAccessory( "Accessory Value", "1000" );
        return _page;
    }

}
