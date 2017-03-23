package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Assert;

import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;

public class ManuallyRatedBusinessLiabilityPage
{

    public static String basePremium = "$55.55";

    // Edit page and assert these new values
    public static String newBasePremium = "$66.66";

    public static void assertPremiumValues( BusinessLiabilityPage page )
    {
        Assert.assertEquals( basePremium, page.getSectionTotalBasePremiumValue() );
    }

    public static void assertNewPremiumValues( BusinessLiabilityPage page )
    {
        Assert.assertEquals( newBasePremium, page.getSectionTotalBasePremiumValue() );
    }
}
