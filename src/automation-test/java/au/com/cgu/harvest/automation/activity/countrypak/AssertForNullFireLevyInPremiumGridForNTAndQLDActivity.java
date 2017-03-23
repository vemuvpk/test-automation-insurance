package au.com.cgu.harvest.automation.activity.countrypak;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumRow;

@Activity( "Exercise the Countrypak premium grid and Check for Fire levy" )
public class AssertForNullFireLevyInPremiumGridForNTAndQLDActivity extends
    AbstractActivity< PremiumPage >
{
    private HarvestPage _page;

    public AssertForNullFireLevyInPremiumGridForNTAndQLDActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PremiumPage execute()
    {
        PremiumPage premiumPage = _page.getNavigationTree().navigateToCountrypakPremium();
        PremiumRow row = premiumPage.getPremiumGrid().getFooterRow();
        assertEquals( "Policy Total", row.getRisk() );
        assertEquals( "$0.00", row.getFireLevy() );

        return premiumPage;
    }
}
