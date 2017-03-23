package au.com.cgu.harvest.automation.activity.countrypak;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumRow;
import au.com.cgu.harvest.pages.PrimitiveWait;

@Activity( "Exercise the Countrypak premium grid and Check for Null Stamp Duty" )
public class AssertForNullStampDutyInPremiumGridForNTAndQLDActivity extends
    AbstractActivity< PremiumPage >
{
    private HarvestPage _page;

    public AssertForNullStampDutyInPremiumGridForNTAndQLDActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PremiumPage execute()
    {
        PremiumPage premiumPage = _page.getNavigationTree().navigateToCountrypakPremium();
        PrimitiveWait.forMillis( 500 );
        PremiumRow row = premiumPage.getPremiumGrid().getFooterRow();
        assertEquals( "Policy Total", row.getRisk() );
        assertEquals( "$0.00", row.getFireLevy() );
        assertEquals( "$0.00", row.getStampDuty() );

        return premiumPage;
    }
}
