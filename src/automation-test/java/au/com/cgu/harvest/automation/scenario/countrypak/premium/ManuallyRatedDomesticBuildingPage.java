package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Assert;

import au.com.cgu.harvest.automation.DodgyAssert;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;

public class ManuallyRatedDomesticBuildingPage
{

    public static String dwellingTypeBasePremium = "$166.65";
    public static String additionalBusinessContentsBasePremium = "$55.55";
    public static String unspecifiedValuablesBasePremium = "$50.00";
    public static String specifiedValuablesBasePremium = "$55.55";
    public static String sectionTotalBasePremium = "$222.2";

    // Edit Domestic Building and Contents page and add these new values
    public static String newDwellingTypeBasePremium = "$66.66";
    public static String newAdditionalBusinessContentsBasePremium = "$66.66";
    public static String newUnspecifiedValuablesBasePremium = "$66.66";
    public static String newSpecifiedValuablesBasePremium = "$66.66";
    public static String newSectionTotalBasePremium = "$266.64";

    public static void assertPremiumValues( DomesticBuildingAndContentsPage page )
    {
        Assert.assertEquals( dwellingTypeBasePremium, page.getDwellingTypeBasePremium() );
        Assert.assertEquals( additionalBusinessContentsBasePremium,
            page.getAdditionalContentsBasePremium() );
        DodgyAssert.assertNearlyEquals( sectionTotalBasePremium, page.getSectionTotalBasePremium() );
    }

    public static void assertNewPremiumValues( DomesticBuildingAndContentsPage page )
    {
        Assert.assertEquals( newDwellingTypeBasePremium, page.getDwellingTypeBasePremium() );
        Assert.assertEquals( newAdditionalBusinessContentsBasePremium,
            page.getAdditionalContentsBasePremium() );
        Assert.assertEquals( newSectionTotalBasePremium, page.getSectionTotalBasePremium() );
    }

}
