package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.DodgyAssert;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Premium values in farm machinery and Working Dogs Page Activity" )
public class AssertPremiumsInFarmMachineryActivity extends
    AbstractActivity< FarmMachineryAndWorkingDogsPage >
{
    private HarvestPage _page;
    private String _fmPremium;
    private String _uSFMPremium;
    private String _wdPremium;
    private String _sectionTotalPremium;

    public AssertPremiumsInFarmMachineryActivity( HarvestPage page, String fmPremium,
        String uSFMPremium, String wdPremium, String sectionTotalPremium )
    {
        _page = page;
        _fmPremium = fmPremium;
        _uSFMPremium = uSFMPremium;
        _wdPremium = wdPremium;
        _sectionTotalPremium = sectionTotalPremium;
    }

    @Override
    protected FarmMachineryAndWorkingDogsPage execute()
    {
        FarmMachineryAndWorkingDogsPage farmMachineryAndWorkingDogsPage =
            _page.getNavigationTree().navigateToFarmMachinery();

        DodgyAssert.assertNearlyEquals( _fmPremium,
                farmMachineryAndWorkingDogsPage.getFarmMachineryPremiumValue() );
        DodgyAssert.assertNearlyEquals( _uSFMPremium,
            farmMachineryAndWorkingDogsPage.getUnspecifiedFarmMachineryPremiumValue() );
        DodgyAssert.assertNearlyEquals( _wdPremium,
            farmMachineryAndWorkingDogsPage.getWorkingDogsPremiumValue() );
        DodgyAssert.assertNearlyEquals( _sectionTotalPremium,
            farmMachineryAndWorkingDogsPage.getSectionTotalBasePremiumValue() );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.FARM_MACHINERY );
        section.isTaken();

        return farmMachineryAndWorkingDogsPage;
    }
}
