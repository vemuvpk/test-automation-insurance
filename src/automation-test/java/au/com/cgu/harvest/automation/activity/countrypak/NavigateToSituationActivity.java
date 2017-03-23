package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Navigate to Situation in Countrypak" )
public class NavigateToSituationActivity extends
    AbstractActivity< SituationDetailPage >

{
    private HarvestPage _page;
    private int _situation;

    public NavigateToSituationActivity( HarvestPage page, int situation )
    {
        _page = page;
        _situation = situation;
    }

    @Override
    protected SituationDetailPage execute()
    {
        SituationDetailPage situationPage =
            _page.getNavigationTree().navigateToSituation( _situation );
        return situationPage;
    }
}
