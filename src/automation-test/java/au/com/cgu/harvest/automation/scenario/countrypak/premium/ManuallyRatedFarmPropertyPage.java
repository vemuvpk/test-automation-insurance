package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Assert;

import au.com.cgu.harvest.automation.DodgyAssert;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

public class ManuallyRatedFarmPropertyPage
{
    public static String unspecifiedFarmBuildingBasePremium = "$55.55";
    public static String buildingTypeBasePremium = "$111.10";
    public static String inAnyOneBuildingbasePremium = "$55.55";
    public static String aboveGroundBasePremium = "$55.55";
    public static String specifiedFarmImprovementsBasePremium = "$55.55";
    public static String specifiedItemsBasePremium = "$55.55";
    public static String sectionTotalBasePremium = "$333.30";

    // EditFarm Property page and add these new values
    public static String newunspecifiedFarmBuildingBasePremium = "$66.66";
    public static String newinAnyOneBuildingbasePremium = "$66.66";
    public static String newaboveGroundBasePremium = "$66.66";
    public static String newspecifiedFarmImprovementsBasePremium = "$66.66";
    public static String newspecifiedItemsBasePremium = "$66.66";
    public static String newsectionTotalBasePremium = "$444.40";

    public static void assertPremiumValues( FarmPropertyPage page )
    {
        Assert.assertEquals( unspecifiedFarmBuildingBasePremium,
            page.getUnspecifiedFarmBuildingBasePremium() );
        Assert.assertEquals( buildingTypeBasePremium, page.getBuildingTypeBasePremium() );
        Assert.assertEquals( inAnyOneBuildingbasePremium, page.getInAnyOneBuildingBasePremium() );
        Assert.assertEquals( aboveGroundBasePremium,
            page.getAboveGroundFarmImprovementsBasePremium() );
        DodgyAssert.assertNearlyEquals( specifiedFarmImprovementsBasePremium,
            page.getFarmImprovementsBasePremium() );
        DodgyAssert.assertNearlyEquals( sectionTotalBasePremium, page.getSectionTotalBasePremium() );
    }

    public static void assertNewPremiumValues( FarmPropertyPage page )
    {
        Assert.assertEquals( newunspecifiedFarmBuildingBasePremium,
            page.getUnspecifiedFarmBuildingBasePremium() );
        Assert.assertEquals( newinAnyOneBuildingbasePremium, page.getInAnyOneBuildingBasePremium() );
        Assert.assertEquals( newaboveGroundBasePremium,
            page.getAboveGroundFarmImprovementsBasePremium() );
        Assert.assertEquals( newspecifiedFarmImprovementsBasePremium,
            page.getFarmImprovementsBasePremium() );
        Assert.assertEquals( newspecifiedItemsBasePremium, page.getSpecifiedItemsBasePremium() );
        Assert.assertEquals( newsectionTotalBasePremium, page.getSectionTotalBasePremium() );
    }
}
