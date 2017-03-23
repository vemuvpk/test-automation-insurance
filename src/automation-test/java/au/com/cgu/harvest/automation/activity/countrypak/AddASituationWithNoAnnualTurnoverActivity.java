package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Create a Situation With no Annual Turnover Activity" )
public class AddASituationWithNoAnnualTurnoverActivity extends
    AbstractActivity< SituationDetailPage >
{
    private HarvestPage _page;

    public AddASituationWithNoAnnualTurnoverActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected SituationDetailPage execute()
    {
        SituationDetailPage createSituationLevelSectionPage =
            _page.getToolbar().addSituation();
        createSituationLevelSectionPage.setAddressLine1( "Situation - 1" );
        createSituationLevelSectionPage.setSuburbStatePostcode( "Sydney", "NSW", "2000" );
        createSituationLevelSectionPage.setPropertySize( "2000" );
        createSituationLevelSectionPage.setOccupation( "Pig Farming (0151)" );

        return createSituationLevelSectionPage;
    }
}
