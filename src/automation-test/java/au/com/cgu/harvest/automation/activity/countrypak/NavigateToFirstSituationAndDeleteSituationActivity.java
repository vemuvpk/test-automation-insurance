package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Navigate to First Situation and Delete the Situation from the Page in Countrypak" )
public class NavigateToFirstSituationAndDeleteSituationActivity extends
    AbstractActivity< PolicyDetailPage >

{
    private HarvestPage _page;

    public NavigateToFirstSituationAndDeleteSituationActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        SituationDetailPage situationPage = _page.getNavigationTree().navigateToSituation( 1 );
        Assert.assertEquals( "Situation - 1", situationPage.getAddressLine1() );
        return situationPage.delete();
    }
}
