package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.DeleteSituationPopup;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Navigate to Situation and Delete the Situation from the Situation Page in Countrypak" )
public class DeleteSituationWithReasonActivity extends
    AbstractActivity< DeleteSituationPopup >

{
    private HarvestPage _page;
    private int _situationNumber;

    public DeleteSituationWithReasonActivity( HarvestPage page, int situationNumber )
    {
        _page = page;
        _situationNumber = situationNumber;
    }

    @Override
    protected DeleteSituationPopup execute()
    {
        SituationDetailPage situationPage =
            _page.getNavigationTree().navigateToSituation( _situationNumber );
        situationPage.deleteWithReason();
        return new DeleteSituationPopup( getWebDriver() );
    }
}
