package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Premium values in Business Liability Page Activity" )
public class AssertPremiumsInBusinessLiabilityPageActivity extends
    AbstractActivity< BusinessLiabilityPage >
{
    private HarvestPage _page;
    private String _sectionTotalPremium;

    public AssertPremiumsInBusinessLiabilityPageActivity( HarvestPage page,
        String sectionTotalPremium )
    {
        _page = page;
        _sectionTotalPremium = sectionTotalPremium;
    }

    @Override
    protected BusinessLiabilityPage execute()
    {
        BusinessLiabilityPage businessLiabilityPage =
            _page.getNavigationTree().navigateToBusinessLiability();

        Assert.assertEquals( _sectionTotalPremium,
            businessLiabilityPage.getSectionTotalBasePremiumValue() );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.BUSINESS_LIABILITY );
        section.isTaken();

        return businessLiabilityPage;
    }
}
