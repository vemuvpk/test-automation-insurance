package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Add a Situation in Countrypak Situation Level Sections Activity" )
public class AddASituationActivity extends
    AbstractActivity< SituationDetailPage >
{
    private HarvestPage _page;

    public AddASituationActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected SituationDetailPage execute()
    {
        SituationDetailPage addSituation =
            _page.getToolbar().addSituation();
        addSituation.setAddressLine1( "Situation-2" );
        addSituation.setSuburbStatePostcode( "Molong", "NSW", "2866" );
        addSituation.setPropertySize( "2000" );
        addSituation.setAnnualTurnOver( "100000" );
        addSituation.setOccupation( "Pig Farming (0151)" );

        return addSituation;
    }
}
