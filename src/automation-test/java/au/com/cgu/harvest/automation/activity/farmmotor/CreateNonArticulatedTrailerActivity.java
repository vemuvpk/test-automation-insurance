package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.NonArticulatedTrailersPage;

@Activity( "Create a Non Articulated Trailer in a manner that allows a policy to be completed" )
public class CreateNonArticulatedTrailerActivity extends
    AbstractActivity< NonArticulatedTrailersPage >
{
    private HarvestPage _page;

    public CreateNonArticulatedTrailerActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NonArticulatedTrailersPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        NonArticulatedTrailersPage vehicleDetailPage = addVehiclePage.addNonArticulatedTrailers();
        vehicleDetailPage.setYearOfManufacture( "2010" );
        vehicleDetailPage.setTypeOfTrailer( "Jinker Trailer" );
        vehicleDetailPage.setVehicleIdentificationDetails( "true", "VEMU", "VIN" );
        vehicleDetailPage.setSuburbStatePostcode( "Molong", "NSW", "2866" );
        vehicleDetailPage.setAtFaultClaimFreeYearsAndProof( "4 years", "true" );
        vehicleDetailPage.setVehicleValue( "$500" );
        vehicleDetailPage.setExistingUnrepairedDamage( "false" );
        vehicleDetailPage.setDoesVehicleOperateOutsideTheBase( "false" );
        vehicleDetailPage.setYearOfBirthOfUsualDriver( "1979" );
        vehicleDetailPage.setInterestedParty( "false" );
        vehicleDetailPage.setAfterMarketAccessoriesAndModifications( false, false );

        return vehicleDetailPage;
    }
}
