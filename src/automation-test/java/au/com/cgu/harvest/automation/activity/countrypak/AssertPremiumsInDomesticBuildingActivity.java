package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Premiums in Domestic Building Page Activity" )
public class AssertPremiumsInDomesticBuildingActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private HarvestPage _page;

    private String _dwellingTypeTotalBasePremium;
    private String _additionalContentsBasePremium;
    private String _unspecifiedValuablesBasePremium;
    private String _specifiedValuablesBasePremium;
    private String _sectionTotalBasePremium;

    public AssertPremiumsInDomesticBuildingActivity( HarvestPage page,
        String domesticBuildingTotalBasePremium,
        String additionalContentsBasePremium, String unspecifiedValuablesBasePremium,
        String specifiedValuablesBasePremium, String sectionTotalBasePremium )
    {
        _page = page;
        _dwellingTypeTotalBasePremium = domesticBuildingTotalBasePremium;
        _additionalContentsBasePremium = additionalContentsBasePremium;
        _unspecifiedValuablesBasePremium = unspecifiedValuablesBasePremium;
        _specifiedValuablesBasePremium = specifiedValuablesBasePremium;
        _sectionTotalBasePremium = sectionTotalBasePremium;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            _page.getNavigationTree().navigateToDomesticBuilding( 1 );

        Assert
            .assertEquals( _dwellingTypeTotalBasePremium,
                domesticBuildingAndContentsPage.getDwellingTypeBasePremium() );
        Assert
            .assertEquals( _additionalContentsBasePremium,
                domesticBuildingAndContentsPage.getAdditionalContentsBasePremium() );
        Assert
            .assertEquals( _sectionTotalBasePremium,
                domesticBuildingAndContentsPage.getSectionTotalBasePremium() );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.DOMESTIC_BUILDING, 1 );
        section.isTaken();

        return domesticBuildingAndContentsPage;
    }
}
