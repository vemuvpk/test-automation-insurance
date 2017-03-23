package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;

@Activity( "Assert the changed Vehcile Rating Details are saved and displayed" )
public class AssertAgriculturalVehicleRatingDetails extends
    AbstractActivity< AgriculturalVehiclePage >
{
    private AgriculturalVehiclePage _page;

    public AssertAgriculturalVehicleRatingDetails( AgriculturalVehiclePage page )
    {
        _page = page;
    }

    @Override
    protected AgriculturalVehiclePage execute()
    {
        AgriculturalVehiclePage vehicleDetailPage =
            _page.getNavigationTree().navigateToAgriculturalVehicle( 1 );

        assertEquals( "2131", _page.getGaragePostcode() );
        assertEquals( "ASHFIELD", _page.getGarageSuburb() );
        assertEquals( "NSW", _page.getGarageStateCode() );
        assertEquals( "$13,500", _page.getVehicleValue() );
        assertEquals( "Comprehensive", _page.getTypeOfCover() );
        assertEquals( "50000000.000", _page.getLimitOfLiability() );
        assertEquals( "$500", _page.getCompulsoryExcess() );
        assertEquals( "1982", _page.getYearOfBirthOfUsualDriver() );

        return vehicleDetailPage;
    }
}
