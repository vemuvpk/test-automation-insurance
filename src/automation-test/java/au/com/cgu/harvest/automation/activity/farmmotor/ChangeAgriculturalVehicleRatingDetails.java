package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;

@Activity( "Change Agricultural Vehicle Rating Details" )
public class ChangeAgriculturalVehicleRatingDetails extends
    AbstractActivity< AgriculturalVehiclePage >
{
    private AgriculturalVehiclePage _page;

    public ChangeAgriculturalVehicleRatingDetails( AgriculturalVehiclePage page )
    {
        _page = page;
    }

    @Override
    protected AgriculturalVehiclePage execute()
    {
        _page.setVehicleRatingDetails( "2131", "Ashfield", "NSW", "$13,500", "Comprehensive",
            "50000000.000", "Standard Excess", "500", "1982" );
        return _page;
    }
}
