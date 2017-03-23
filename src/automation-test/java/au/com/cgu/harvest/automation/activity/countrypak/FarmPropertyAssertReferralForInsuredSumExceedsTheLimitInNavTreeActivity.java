package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Referral For Farm Property - When the Sum Insured Exceeds the Authorised Limit Activity" )
public class FarmPropertyAssertReferralForInsuredSumExceedsTheLimitInNavTreeActivity
    extends
    AbstractActivity< FarmPropertyPage >
{
    private HarvestPage _page;

    public FarmPropertyAssertReferralForInsuredSumExceedsTheLimitInNavTreeActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyPage farmPropertyPage =
            _page.getNavigationTree().navigateToFarmProperty( 1 );

        farmPropertyPage.setAllFarmContentsLimit( "3000000" );
        _page.getNavigationTree().getSection( SectionType.FARM_PROPERTY, 1 )
            .containsRule( "R040" );
        return farmPropertyPage;

    }
}
