package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.DodgyAssert;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Premium values in Machinery Breakdown Page Activity" )
public class AssertPremiumsInMachineryBreakdownPageActivity extends
    AbstractActivity< MachineryBreakdownPage >
{
    private HarvestPage _page;
    private String _blanketCoverBasePremium;
    private String _specifiedItemsPremium;
    private String _sectionTotalPremium;

    public AssertPremiumsInMachineryBreakdownPageActivity( HarvestPage page,
        String blanketCoverBasePremium, String specifiedItemsPremium, String sectionTotalPremium )
    {
        _page = page;
        _blanketCoverBasePremium = blanketCoverBasePremium;
        _specifiedItemsPremium = specifiedItemsPremium;
        _sectionTotalPremium = sectionTotalPremium;
    }

    @Override
    protected MachineryBreakdownPage execute()
    {
        MachineryBreakdownPage machineryBreakdownPage =
            _page.getNavigationTree().navigateToMachineryBreakdown();
        DodgyAssert.assertNearlyEquals( _blanketCoverBasePremium,
            machineryBreakdownPage.getblanketCoverBasePremiumValue() );
        DodgyAssert.assertNearlyEquals( _specifiedItemsPremium,
            machineryBreakdownPage.getSpecifiedItemsPremiumValue() );
        DodgyAssert.assertNearlyEquals( _sectionTotalPremium,
            machineryBreakdownPage.getSectionTotalBasePremiumValue() );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.MACHINERY_BREAKDOWN );
        section.isTaken();

        return machineryBreakdownPage;
    }
}
