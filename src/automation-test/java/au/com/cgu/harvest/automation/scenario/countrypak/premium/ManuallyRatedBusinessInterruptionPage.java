package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Assert;

import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;

public class ManuallyRatedBusinessInterruptionPage
{

    public static String agistmentIncomePremium = "$55.55";
    public static String farmContinuationPremium = "$55.55";

    // Edit hay Fences page and add these new values
    public static String newBoundaryNotSharedPremium = "$66.66";
    public static String newBoundarySharedPremium = "$66.66";

    public static void assertPremiumValues( BusinessInterruptionPage page )
    {
        Assert.assertEquals( agistmentIncomePremium, page.getAgistmentIncomePremiumValue() );
        Assert.assertEquals( farmContinuationPremium,
            page.getFarmContinuationExpensesPremiumValue() );
    }

    public static void assertNewPremiumValues( BusinessInterruptionPage page )
    {
        Assert.assertEquals( newBoundaryNotSharedPremium, page.getAgistmentIncomePremiumValue() );
        Assert.assertEquals( newBoundarySharedPremium,
            page.getFarmContinuationExpensesPremiumValue() );
    }
}
