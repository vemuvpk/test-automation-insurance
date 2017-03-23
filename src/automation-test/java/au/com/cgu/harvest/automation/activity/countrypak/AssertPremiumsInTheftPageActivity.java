package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.DodgyAssert;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.SectionType;
import au.com.cgu.harvest.pages.countrypak.TheftPage;

@Activity( "Assert Premium values in Theft Page Activity" )
public class AssertPremiumsInTheftPageActivity extends
    AbstractActivity< TheftPage >
{
    private HarvestPage _page;
    private String _farmContentsSituationPremium;
    private String _farmContentsPremium;
    private String _farmMachineryPremium;
    private String _sectionTotalPremium;

    public AssertPremiumsInTheftPageActivity( HarvestPage page,
        String farmContentsSituationPremium, String farmContentsPremium,
        String farmMachineryPremium, String sectionTotalPremium )
    {
        _page = page;
        _farmContentsSituationPremium = farmContentsSituationPremium;
        _farmContentsPremium = farmContentsPremium;
        _farmMachineryPremium = farmMachineryPremium;
        _sectionTotalPremium = sectionTotalPremium;
    }

    @Override
    protected TheftPage execute()
    {
        TheftPage theftPage =
            _page.getNavigationTree().navigateToTheft();

        DodgyAssert.assertNearlyEquals( _farmContentsSituationPremium,
            theftPage.getFarmContentsSituationBasePremiumValue() );
        DodgyAssert.assertNearlyEquals( _farmContentsPremium,
            theftPage.getFarmContentsBasePremiumValue() );
        DodgyAssert.assertNearlyEquals( _farmMachineryPremium,
            theftPage.getFarmMachineryBasePremiumValue() );
        DodgyAssert.assertNearlyEquals( _sectionTotalPremium,
            theftPage.getSectionTotalBasePremiumValue() );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.THEFT );
        section.isTaken();

        return theftPage;
    }
}
