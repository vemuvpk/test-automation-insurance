package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Create a Situation in Countrypak For VIC State Activity" )
public class CreateASituationForVICStateActivity extends
    AbstractActivity< SituationDetailPage >
{
    private final HarvestPage _page;

    public CreateASituationForVICStateActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected SituationDetailPage execute()
    {
        SituationDetailPage createSituationLevelSectionPage =
            _page.getNavigationTree().navigateToSituation( 1 );
        createSituationLevelSectionPage.setAddressLine1( "Situation - 1" );
        createSituationLevelSectionPage.setSuburbStatePostcode( "GLEN WAVERLEY", "VIC", "3150" );
        createSituationLevelSectionPage.setPropertySize( "2000" );
        createSituationLevelSectionPage.setAnnualTurnOver( "100000" );
        createSituationLevelSectionPage.setOccupation( "Pig Farming (0151)" );

        return createSituationLevelSectionPage;
    }
}
