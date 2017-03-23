package au.com.cgu.harvest.automation.activity;

import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.ReferralGridRow;
import au.com.cgu.harvest.pages.ReferralsPageGrid;

@Activity( "Assert Schedule Comment referral in Referral Grid Activity" )
public class AssertTwoReferralsForTwoScheduledCommentsInReferralPanel extends
    AbstractActivity< ReferralsPageGrid >
{
    private HarvestPage _page;
    private String _referralPage;
    private String _referralRule;

    public AssertTwoReferralsForTwoScheduledCommentsInReferralPanel( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected ReferralsPageGrid execute()
    {
        ReferralsPageGrid navPanel =
            _page.getReferralAndErrorConditions().clickOnRreferral();

        ReferralGridRow referralGridRow = navPanel.getReferralAtRow( 1 );
        referralGridRow
            .hasValues( "Comments",
                "Referral - R067: A schedule comment has been added, amended or deleted: 'First Sche...'" );

        referralGridRow = navPanel.getReferralAtRow( 2 );
        referralGridRow
            .hasValues( "Comments",
                "Referral - R067: A schedule comment has been added, amended or deleted: 'Second Sch...'" );

        return new ReferralsPageGrid( getWebDriver() );
    }
}
