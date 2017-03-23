package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.FarmMotorTrailerPage;

@Activity( "Assert Farm Motor Trailer validation Activity" )
public class FarmMotorTrailersAssertForVehicleValidationActivity
    extends
    AbstractActivity< FarmMotorTrailerPage >
{
    private HarvestPage _page;

    public FarmMotorTrailersAssertForVehicleValidationActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMotorTrailerPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        FarmMotorTrailerPage vehicleDetailPage = addVehiclePage.addFarmMotorTrailer();

        // assertVehiclevalidation
        vehicleDetailPage.setExistingUnrepairedDamageValidation( "true", "R021" );
        vehicleDetailPage.setVehicleValueValidation( "$500,001", "D003" );
        vehicleDetailPage.setVehicleValueReferral( "$150,001", "R003" );
        vehicleDetailPage.setAtFaultClaimFreeYears( "4 years" );
        vehicleDetailPage.setProofOfAtFaultClaimFreeYearsvalidation( "false", "R020" );
        vehicleDetailPage.setGaragePostCodeValidation( "2000", "R008" );
        vehicleDetailPage.setYearOfBirthOfUsualDriverValidation( "1800", "E009" );
        vehicleDetailPage.setYearOfBirthOfUsualDriverValidation( "2010", "E012" );

        return vehicleDetailPage;

    }

}
