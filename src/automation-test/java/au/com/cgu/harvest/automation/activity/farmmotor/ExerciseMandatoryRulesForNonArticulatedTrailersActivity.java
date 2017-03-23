package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.NonArticulatedTrailersPage;
import au.com.cgu.harvest.pages.farmmotor.NonArticulatedTrailersPageXpath;

@Activity( "Exercise Mandatory Rules for Non Articulated Trailer activity" )
public class ExerciseMandatoryRulesForNonArticulatedTrailersActivity extends
    AbstractActivity< NonArticulatedTrailersPage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForNonArticulatedTrailersActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NonArticulatedTrailersPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        NonArticulatedTrailersPage vehicleDetailPage = addVehiclePage.addNonArticulatedTrailers();

        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.YEAR_OF_MANUFACTURE );
        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.TYPE_OF_TRAILER );
        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.IS_VEHICLE_REGISTERED );
        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.AT_FAULT_CLAIM_FREE_YEAR );
        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.VEHICLE_VALUE );
        _page
            .mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.EXISTING_UNREPAIRED_DAMAGE );
        _page
            .mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.DOES_VEHICLE_OPERATE_OUTSIDE_BASE );
        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.YEAR_OF_BIRTH );
        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.VEHICLE_UNDER_FINANCE );
        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.AFTER_MARKET_ACCESSORIES );
        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.VEHICLE_MODIFICATIONS );

        vehicleDetailPage.setDoesVehicleOperateOutsideTheBase( "true" );
        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.OPERATING_RADIUS );

        vehicleDetailPage.setAtFaultClaimFreeYears( "4 years" );
        _page
            .mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.OBTAINED_PROOF_OF_CLAIM_YEAR );

        vehicleDetailPage.setIsRegistered( "true" );
        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.REGISTRATION_NUMBER );

        vehicleDetailPage.setTypeOfTrailer( "Other" );
        _page.mandatoryErrorDisplayedFor( NonArticulatedTrailersPageXpath.DESCRIPTION_OF_TRAILER );

        return vehicleDetailPage;

    }
}
