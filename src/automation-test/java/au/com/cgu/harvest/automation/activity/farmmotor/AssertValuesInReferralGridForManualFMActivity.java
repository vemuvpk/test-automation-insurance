package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.ReferralAndErrorConditions;
import au.com.cgu.harvest.pages.ReferralsPageGrid;

@Activity( "Assert Values in Referral Grid for manual Farm motor policy Activity" )
public class AssertValuesInReferralGridForManualFMActivity extends
    AbstractActivity< ReferralsPageGrid >
{
    private HarvestPage _page;
    private String _referralPage;
    private String _referralRule;

    public AssertValuesInReferralGridForManualFMActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected ReferralsPageGrid execute()
    {
        ReferralAndErrorConditions navPanel =
            _page.getReferralAndErrorConditions();

        navPanel.clickOnRreferral();

        ReferralsPageGrid referralGrid = navPanel.getReferralsPageGrid();
        referralGrid.getReferralAtRow( 1 ).getValues();
        referralGrid
            .getReferralAtRow( 1 )
            .hasValues( "MANUAL HOLDEN BARINA",
                "Referral - R036: Vehicle base premium could not be determined" );
        referralGrid
            .getReferralAtRow( 2 )
            .hasValues( "MANUAL HOLDEN BARINA",
                "Referral - R036: Vehicle excess could not be determined" );

        return referralGrid;
    }
}
