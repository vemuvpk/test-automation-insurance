package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Navigate to Situation - 1 and check Situation-2 details are present" )
public class AssertSituation2InSituation1Activity extends
    AbstractActivity< SituationDetailPage >

{
    private HarvestPage _page;

    public AssertSituation2InSituation1Activity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected SituationDetailPage execute()
    {
        SituationDetailPage situationPage = _page.getNavigationTree().navigateToSituation( 1 );
        Assert.assertEquals( "Situation-2", situationPage.getAddressLine1() );
        return situationPage;
    }
}
