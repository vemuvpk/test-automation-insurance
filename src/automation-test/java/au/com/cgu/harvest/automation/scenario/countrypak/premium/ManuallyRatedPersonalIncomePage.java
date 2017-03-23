package au.com.cgu.harvest.automation.scenario.countrypak.premium;

import org.junit.Assert;

import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;

public class ManuallyRatedPersonalIncomePage
{

    public static String accidentPremium = "$55.55";
    public static String illnessPremium = "$55.55";

    // Edit page and assert these new values
    public static String newAccidentPremium = "$66.66";
    public static String newIllnessPremium = "$66.66";

    public static void assertPremiumValues( PersonalIncomePage page )
    {
        Assert.assertEquals( accidentPremium, page.getAccidentBasePremiumValue() );
        Assert.assertEquals( illnessPremium, page.getIllnessBasePremiumValue() );
    }

    public static void assertNewPremiumValues( PersonalIncomePage page )
    {
        Assert.assertEquals( newAccidentPremium, page.getAccidentBasePremiumValue() );
        Assert.assertEquals( newIllnessPremium, page.getIllnessBasePremiumValue() );
    }
}
