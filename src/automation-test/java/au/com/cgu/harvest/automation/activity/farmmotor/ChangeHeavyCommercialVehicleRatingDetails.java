package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePage;

@Activity( "Change Heavy Commercial Vehicle Rating Details" )
public class ChangeHeavyCommercialVehicleRatingDetails extends
    AbstractActivity< HeavyCommercialVehiclePage >
{
    private HeavyCommercialVehiclePage _page;

    public ChangeHeavyCommercialVehicleRatingDetails( HeavyCommercialVehiclePage page )
    {
        _page = page;
    }

    @Override
    protected HeavyCommercialVehiclePage execute()
    {
        _page.setRegistrationNumber( "1111" );
        _page.setSuburbStatePostcode( "Sydney", "NSW", "2000" );
        _page.setYearOfBirthOfUsualDriver( "2010" );
        _page.setVehicleIdentificationNumber( "VINCIN" );
        _page.setVehicleValue( "Comprehensive", "500001", "D003" );

        return _page;
    }
}
