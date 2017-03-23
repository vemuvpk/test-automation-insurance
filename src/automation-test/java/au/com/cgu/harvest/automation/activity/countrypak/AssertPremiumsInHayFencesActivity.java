package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.DodgyAssert;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Premium values in Hay Fences and LiveStock Page Activity" )
public class AssertPremiumsInHayFencesActivity extends
    AbstractActivity< HayFencesLiveStockPage >
{
    private HarvestPage _page;
    private String _boundaryNotSharedPremium;
    private String _boundarySharedPremium;
    private String _otherFencingPremium;
    private String _sheepPremium;
    private String _hayGrainPremium;
    private String _farmTreesPremium;
    private String _sectionTotalPremium;

    public AssertPremiumsInHayFencesActivity( HarvestPage page, String boundaryNotSharedPremium,
        String boundarySharedPremium, String fencingPremium, String sheepPremium,
        String hayGrainPremium, String farmTreesPremium, String sectionTotalPremium )
    {
        _page = page;
        _boundaryNotSharedPremium = boundaryNotSharedPremium;
        _boundarySharedPremium = boundarySharedPremium;
        _otherFencingPremium = fencingPremium;
        _sheepPremium = sheepPremium;
        _hayGrainPremium = hayGrainPremium;
        _farmTreesPremium = farmTreesPremium;
        _sectionTotalPremium = sectionTotalPremium;
    }

    @Override
    protected HayFencesLiveStockPage execute()
    {
        HayFencesLiveStockPage hayFencesLiveStockPage =
            _page.getNavigationTree().navigateToHayFenceLiveStock( 1 );

        DodgyAssert.assertNearlyEquals( _boundaryNotSharedPremium,
                hayFencesLiveStockPage.getBoundaryNotSharedPremiumValue() );
        DodgyAssert.assertNearlyEquals( _boundarySharedPremium,
            hayFencesLiveStockPage.getBoundarySharedPremiumValue() );
        DodgyAssert.assertNearlyEquals( _otherFencingPremium,
            hayFencesLiveStockPage.getAllOtherFencingPremiumValue() );
        DodgyAssert.assertNearlyEquals( _sheepPremium,
            hayFencesLiveStockPage.getSheepPremiumValue() );
        DodgyAssert.assertNearlyEquals( _hayGrainPremium,
            hayFencesLiveStockPage.getHayAndGrainPremiumValue() );
        DodgyAssert.assertNearlyEquals( _farmTreesPremium,
            hayFencesLiveStockPage.getFarmTreesPremiumValue() );
        DodgyAssert.assertNearlyEquals( _sectionTotalPremium,
            hayFencesLiveStockPage.getSectionTotalBasePremiumValue() );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.HAY_FENCES_LIVESTOCK, 1 );
        section.isTaken();

        return hayFencesLiveStockPage;
    }
}
