package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Navigate to Situation and Delete the Situation from the Situation Page for total loss in Countrypak" )
public class DeleteSituationForTotalLossActivity extends
    AbstractActivity< PolicyDetailPage >

{
    private HarvestPage _page;
    private int _situationNumber;

    public DeleteSituationForTotalLossActivity( HarvestPage page, int situationNumber )
    {
        _page = page;
        _situationNumber = situationNumber;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        SituationDetailPage situationPage =
            _page.getNavigationTree().navigateToSituation( _situationNumber );
        return situationPage.deleteForTotalLoss();
    }
}
