package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;

@Activity( "Change Rating Factor and Assert that Premium value is Blanked out Activity" )
public class ChangeRatingFactorInBusinessInterruptionPageActivity extends
    AbstractActivity< BusinessInterruptionPage >
{
    private HarvestPage _page;

    public ChangeRatingFactorInBusinessInterruptionPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessInterruptionPage execute()
    {
        BusinessInterruptionPage businessInterruptionPage =
            _page.getNavigationTree().navigateToBusinessInterruption();

        businessInterruptionPage.setAgistmentIncomeSumInsured( "3000" );
        businessInterruptionPage.setFarmContinuationExpensesSumInsured( "30000" );
        
        Assert.assertEquals( "", businessInterruptionPage.getAgistmentIncomePremiumValue());
        Assert.assertEquals( "", businessInterruptionPage.getFarmContinuationExpensesPremiumValue() );

        return businessInterruptionPage;
    }
}
