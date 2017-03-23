package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Add a Situation in Countrypak Situation Level Sections Activity" )
public class AddMultipleSituationActivity extends
    AbstractActivity< SituationDetailPage >
{
    private HarvestPage _page;
    private String _addressLine;

    public AddMultipleSituationActivity( HarvestPage page, String addressLine )
    {
        _page = page;
        _addressLine = addressLine;
    }

    @Override
    protected SituationDetailPage execute()
    {
        SituationDetailPage addSituation =
            _page.getToolbar().addSituation();
        addSituation.setAddressLine1( _addressLine );
        addSituation.setSuburbStatePostcode( "Molong", "NSW", "2866" );
        addSituation.setPropertySize( "2000" );
        addSituation.setAnnualTurnOver( "100000" );
        addSituation.setOccupation( "Pig Farming (0151)" );

        return addSituation;
    }
}
