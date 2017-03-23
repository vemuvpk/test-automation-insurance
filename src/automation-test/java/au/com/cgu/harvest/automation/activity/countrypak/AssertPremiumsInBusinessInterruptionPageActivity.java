package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Premium values in Business Interruption Page Activity" )
public class AssertPremiumsInBusinessInterruptionPageActivity extends
    AbstractActivity< BusinessInterruptionPage >
{
    private HarvestPage _page;
    private String _agistmentIncomePremium;
    private String _farmContinuationPremium;
    private String _sectionTotalPremium;

    public AssertPremiumsInBusinessInterruptionPageActivity( HarvestPage page,
        String agistmentIncomePremium, String farmContinuationPremium, String sectionTotalPremium )
    {
        _page = page;
        _agistmentIncomePremium = agistmentIncomePremium;
        _farmContinuationPremium = farmContinuationPremium;
        _sectionTotalPremium = sectionTotalPremium;
    }

    @Override
    protected BusinessInterruptionPage execute()
    {
        BusinessInterruptionPage businessInterruptionPage =
            _page.getNavigationTree().navigateToBusinessInterruption();

        Assert.assertEquals( _agistmentIncomePremium,
            businessInterruptionPage.getAgistmentIncomePremiumValue() );
        Assert.assertEquals( _farmContinuationPremium,
            businessInterruptionPage.getFarmContinuationExpensesPremiumValue() );
        Assert.assertEquals( _sectionTotalPremium,
            businessInterruptionPage.getSectionTotalBasePremiumValue() );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.BUSINESS_INTERRUPTION );
        section.isTaken();

        return businessInterruptionPage;
    }
}
