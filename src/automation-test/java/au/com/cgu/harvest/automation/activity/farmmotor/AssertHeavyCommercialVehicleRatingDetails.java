package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.HeavyCommercialVehiclePage;

@Activity( "Assert the changed Vehcile Rating Details are saved and displayed" )
public class AssertHeavyCommercialVehicleRatingDetails extends
    AbstractActivity< HeavyCommercialVehiclePage >
{
    private HeavyCommercialVehiclePage _page;

    public AssertHeavyCommercialVehicleRatingDetails( HeavyCommercialVehiclePage page )
    {
        _page = page;
    }

    @Override
    protected HeavyCommercialVehiclePage execute()
    {
        HeavyCommercialVehiclePage vehicleDetailPage =
            _page.getNavigationTree().navigateToHeavyCommercialVehicle( 1 );
        _page.setRegistrationNumber( "1111" );
        _page.setSuburbStatePostcode( "Sydney", "NSW", "2000" );
        _page.setYearOfBirthOfUsualDriver( "2010" );
        _page.setVehicleIdentificationNumber( "VINCIN" );
        _page.setVehicleValue( "Comprehensive", "500001", "D003" );
        assertEquals( "2000", _page.getGaragePostcode() );
        assertEquals( "SYDNEY", _page.getGarageSuburb() );
        assertEquals( "NSW", _page.getGarageStateCode() );
        assertEquals( "$500,001", _page.getVehicleValue() );
        assertEquals( "VINCIN", _page.getVehicleIdentificationNumber() );
        assertEquals( "1111", _page.getRegistrationNumber() );

        return vehicleDetailPage;

    }
}
