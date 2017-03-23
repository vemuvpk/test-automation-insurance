package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Assert;

import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;

public class ManuallyRatedHayFencesPage
{

    public static String boundaryNotSharedPremium = "$55.55";
    public static String boundarySharedPremium = "$55.55";
    public static String allOtherFencingPremium = "$55.55";
    public static String sheepBasePremium = "$55.55";
    public static String hayGrainPremium = "$55.55";
    public static String farmTreesBasePremium = "$55.55";
    
    //Edit hay Fences page and add these new values
    public static String newBoundaryNotSharedPremium = "$66.66";
    public static String newBoundarySharedPremium = "$66.66";
    public static String newAllOtherFencingPremium = "$66.66";
    public static String newSheepBasePremium = "$66.66";
    public static String newHayGrainPremium = "$66.66";
    public static String newFarmTreesBasePremium = "$66.66";
    
    
    public static void assertPremiumValues( HayFencesLiveStockPage page )
    {
        Assert.assertEquals( boundaryNotSharedPremium, page.getBoundaryNotSharedPremiumValue() );
        Assert.assertEquals( boundarySharedPremium, page.getBoundarySharedPremiumValue() );
        Assert.assertEquals( allOtherFencingPremium, page.getAllOtherFencingPremiumValue() );
        Assert.assertEquals( sheepBasePremium, page.getSheepPremiumValue() );
        Assert.assertEquals( hayGrainPremium, page.getHayAndGrainPremiumValue() );
        Assert.assertEquals( farmTreesBasePremium, page.getFarmTreesPremiumValue() );
    }
    
    public static void assertNewPremiumValues( HayFencesLiveStockPage page )
    {
        Assert.assertEquals( newBoundaryNotSharedPremium, page.getBoundaryNotSharedPremiumValue() );
        Assert.assertEquals( newBoundarySharedPremium, page.getBoundarySharedPremiumValue() );
        Assert.assertEquals( newAllOtherFencingPremium, page.getAllOtherFencingPremiumValue() );
        Assert.assertEquals( newSheepBasePremium, page.getSheepPremiumValue() );
        Assert.assertEquals( newHayGrainPremium, page.getHayAndGrainPremiumValue() );
        Assert.assertEquals( newFarmTreesBasePremium, page.getFarmTreesPremiumValue() );
    }
}
