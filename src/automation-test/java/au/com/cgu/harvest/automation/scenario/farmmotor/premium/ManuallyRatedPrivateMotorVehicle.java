package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import org.junit.Assert;

import au.com.cgu.harvest.pages.PremiumRow;

public class ManuallyRatedPrivateMotorVehicle
{
    public static String basePremium = "$999.99";
    public static String fireLevy = "$10.00";
    public static String gst = "$101.00";
    public static String stampDuty = "$55.55";
    public static String total = "$1,166.54";

    public static void assertPremiumValues( PremiumRow row )
    {
        Assert.assertEquals( basePremium, row.getTechnicalPremium() );
        Assert.assertEquals( fireLevy, row.getFireLevy() );
        Assert.assertEquals( gst, row.getGst() );
        Assert.assertEquals( stampDuty, row.getStampDuty() );
        Assert.assertEquals( total, row.getTotalPremium() );
    }
}
