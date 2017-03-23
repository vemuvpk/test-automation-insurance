package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PrimitiveWait;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.GlassesVehicleSelectionPopup;
import au.com.cgu.harvest.pages.farmmotor.GlassesVehicleSelectionPopupXpath;

@Activity( "Exercise Mandatory Rules for Unknown Vehicle activity" )
public class ExerciseMandatoryRulesForUnknownVehicleActivity extends
    AbstractActivity< GlassesVehicleSelectionPopup >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForUnknownVehicleActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected GlassesVehicleSelectionPopup execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        GlassesVehicleSelectionPopup popup = addVehiclePage.addUnknownVehicle();
        PrimitiveWait.forMillis( 5000 );
        _page
            .mandatoryErrorDisplayedFor( GlassesVehicleSelectionPopupXpath.UNKNOWN_VEHICLE_DESCRIPTION );

        return popup;

    }
}
