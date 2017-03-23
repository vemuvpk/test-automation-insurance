package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Premiums in Farm Property Page Activity" )
public class AssertPremiumsInFarmPropertyActivity extends
    AbstractActivity< FarmPropertyPage >
{
    private HarvestPage _page;
    private String _unspecifiedBasePremium;
    private String _buildingTotalBasePremium;
    private String _anyOneBuildingPremium;
    private String _aboveGroundFarmBasePremium;
    private String _farmImprovementsBasePremium;
    private String _sectionTotalBasePremium;

    public AssertPremiumsInFarmPropertyActivity( HarvestPage page, String unspecifiedBasePremium,
        String buildingTotalBasePremium, String anyOneBuildingPremium,
        String aboveGroundFarmBasePremium, String farmImprovementsBasePremium,
        String sectionTotalBasePremium )
    {
        _page = page;
        _unspecifiedBasePremium = unspecifiedBasePremium;
        _buildingTotalBasePremium = buildingTotalBasePremium;
        _anyOneBuildingPremium = anyOneBuildingPremium;
        _aboveGroundFarmBasePremium = aboveGroundFarmBasePremium;
        _farmImprovementsBasePremium = farmImprovementsBasePremium;
        _sectionTotalBasePremium = sectionTotalBasePremium;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyPage farmPropertyPage =
            _page.getNavigationTree().navigateToFarmProperty( 1 );

        Assert
            .assertEquals( _unspecifiedBasePremium,
                farmPropertyPage.getUnspecifiedFarmBuildingBasePremium() );
        Assert
            .assertEquals( _buildingTotalBasePremium,
                farmPropertyPage.getBuildingTypeBasePremium() );
        Assert
            .assertEquals( _anyOneBuildingPremium,
                farmPropertyPage.getInAnyOneBuildingBasePremium() );
        Assert
            .assertEquals( _aboveGroundFarmBasePremium,
                farmPropertyPage.getAboveGroundFarmImprovementsBasePremium() );
        Assert
            .assertEquals( _farmImprovementsBasePremium,
                farmPropertyPage.getFarmImprovementsBasePremium() );

        Assert
            .assertEquals( _sectionTotalBasePremium,
                farmPropertyPage.getSectionTotalBasePremium() );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.FARM_PROPERTY, 1 );
        section.isTaken();

        return farmPropertyPage;
    }
}
