package au.com.cgu.harvest.automation.activity.sunrise;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.ReferralAndErrorConditions;
import au.com.cgu.harvest.pages.ReferralsPageGrid;

@Activity( "Assert count of Referrals or Declines Activity" )
public class AssertCountOfReferralsOrDeclinesActivity extends
    AbstractActivity< ReferralAndErrorConditions >
{
    private HarvestPage _page;
    private String _count;

    public AssertCountOfReferralsOrDeclinesActivity( HarvestPage page, String count )
    {
        _page = page;
        _count = count;
    }

    @Override
    protected ReferralAndErrorConditions execute()
    {
        ReferralAndErrorConditions navPanel =
            _page.getReferralAndErrorConditions();

        ReferralsPageGrid referralGrid = navPanel.clickOnRreferral();
        referralGrid.getNumberOfRows();
        assertEquals( _count, navPanel.getCountOfReferrals() );

        // ReferralGridRow referralRow = referralGrid.getReferralAtRow( 1 );
        // referralRow.getValues();

        return navPanel;
    }
}
