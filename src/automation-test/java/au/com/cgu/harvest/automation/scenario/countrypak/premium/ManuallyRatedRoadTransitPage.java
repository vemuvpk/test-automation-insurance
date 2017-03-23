package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Assert;

import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;

public class ManuallyRatedRoadTransitPage
{

    public static String basePremium = "$55.55";

    // Edit page and assert these new values
    public static String newBasePremium = "$66.66";

    public static void assertPremiumValues( RoadTransitPage page )
    {
        Assert.assertEquals( basePremium, page.getBasePremium() );
    }

    public static void assertNewPremiumValues( RoadTransitPage page )
    {
        Assert.assertEquals( newBasePremium, page.getBasePremium() );
    }
}
