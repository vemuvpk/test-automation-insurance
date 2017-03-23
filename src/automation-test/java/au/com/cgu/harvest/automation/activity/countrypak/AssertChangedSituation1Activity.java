package au.com.cgu.harvest.automation.activity.countrypak;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Asert that the changed details in situation page are saved after suspend activity" )
public class AssertChangedSituation1Activity extends AbstractActivity< SituationDetailPage >
{

    private HarvestPage _page;

    public AssertChangedSituation1Activity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected SituationDetailPage execute()
    {
        SituationDetailPage situationPage =
            _page.getNavigationTree().navigateToSituation( 1 );
        assertEquals( "2000", situationPage.getGaragePostcode() );
        assertEquals( "SYDNEY", situationPage.getGarageSuburb() );
        assertEquals( "Situation - 1", situationPage.getAddressLine1() );
        assertEquals( "2000", situationPage.getPropertySize() );
        assertEquals( "$100,000", situationPage.getAnnualTurnOver() );

        return situationPage;

    }

}
