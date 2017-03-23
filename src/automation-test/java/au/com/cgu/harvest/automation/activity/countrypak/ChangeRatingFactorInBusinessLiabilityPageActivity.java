package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;

@Activity( "Change Rating Factor and Assert that Premium value is Blanked out Activity" )
public class ChangeRatingFactorInBusinessLiabilityPageActivity extends
    AbstractActivity< BusinessLiabilityPage >
{
    private HarvestPage _page;

    public ChangeRatingFactorInBusinessLiabilityPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessLiabilityPage execute()
    {
        BusinessLiabilityPage businessLiabilityPage =
            _page.getNavigationTree().navigateToBusinessLiability();

        businessLiabilityPage.setNumberOfWorkingProprietors( "4" );

        Assert.assertEquals( "", businessLiabilityPage.getBasePremiumValue() );

        return businessLiabilityPage;
    }
}
