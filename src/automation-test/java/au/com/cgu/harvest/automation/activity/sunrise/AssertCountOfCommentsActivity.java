package au.com.cgu.harvest.automation.activity.sunrise;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.ReferralAndErrorConditions;

@Activity( "Assert NUmber of Comments Activity" )
public class AssertCountOfCommentsActivity extends AbstractActivity< ReferralAndErrorConditions >
{
    private HarvestPage _page;
    private String _count;

    public AssertCountOfCommentsActivity( HarvestPage page, String count )
    {
        _page = page;
        _count = count;
    }

    @Override
    protected ReferralAndErrorConditions execute()
    {
        ReferralAndErrorConditions navPanel =
            _page.getReferralAndErrorConditions();

        assertEquals( _count, navPanel.getCountOfComments() );

        return navPanel;
    }
}
