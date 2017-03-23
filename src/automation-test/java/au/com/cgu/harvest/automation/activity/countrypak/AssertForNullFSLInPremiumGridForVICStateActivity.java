package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumRow;

@Activity( "Exercise the Countrypak premium grid and Check for NULL FSL" )
public class AssertForNullFSLInPremiumGridForVICStateActivity extends
    AbstractActivity< PremiumPage >
{
    private final HarvestPage _page;
    private static final String CURRENCY_ZERO = "$0.00";

    public AssertForNullFSLInPremiumGridForVICStateActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PremiumPage execute()
    {
        PremiumPage premiumPage = _page.getNavigationTree().navigateToCountrypakPremium();
        PremiumRow row = premiumPage.getPremiumGrid().getFooterRow();

        String amount = row.getFireLevy();
        Assert.assertTrue( String.format( amount, CURRENCY_ZERO ),
            CURRENCY_ZERO.equals( amount ) );

        return premiumPage;
    }
}
