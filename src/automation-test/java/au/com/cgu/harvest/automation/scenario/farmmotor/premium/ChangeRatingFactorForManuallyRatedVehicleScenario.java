package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Change Rating Factor for Manually Rated Vehicle to chk the base premium when both manual and Auto rated vehicles present" )
public class ChangeRatingFactorForManuallyRatedVehicleScenario extends AbstractScenario
{
    @Test
    public void execute()
    {

        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchFarmMotorActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        InsuranceHistoryPage insuranceHistoryPage =
            performActivity( new InsuranceHistoryActivity( policyDetailPage ) );

        PrivateMotorVehiclePage vehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );

        vehiclePage =
            performActivity( new CreatePrivateMotorVehicleWithManualPremiumActivity(
                insuranceHistoryPage ) );

        // VERIFY (19.1) - Change rating Factor for manually rated vehicle and check for base
        // premium
        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        // Get original premium values prior to changing rating factor
        Premium manuallyVehiclePremium = premiumPage.getPremiumForRow( 2 );
        String originalCommission = premiumPage.getCommission();
        String originalCommissionGst = premiumPage.getCommissionGst();

        // Change a rating factor on a manually rated vehicle
        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 2 ) );
        verifyExcessAndBasePremiumClearedRatingFactorChangedOnManualVehicle( vehiclePage );
        vehiclePage.setStandardExcess( "100.00" );
        vehiclePage.setBasePremium( "555.55" );

        premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        Premium manuallyVehiclePremiumAfterRatingFactorChange = premiumPage.getPremiumForRow( 2 );
        String commissionAfterRatingFactorChange = premiumPage.getCommission();
        String commissionGstAfterRatingFactorChange = premiumPage.getCommissionGst();

        PremiumAssert.assertTotalPremiumDecreased( manuallyVehiclePremium,
            manuallyVehiclePremiumAfterRatingFactorChange );

        PremiumAssert.assertZeroPremiumAdjustmentAmount( premiumPage );
        PremiumAssert.assertLessThan( "Commission", commissionAfterRatingFactorChange,
            originalCommission );
        PremiumAssert.assertLessThan( "Commission GST", commissionGstAfterRatingFactorChange,
            originalCommissionGst );

    }

    private void verifyExcessAndBasePremiumClearedRatingFactorChangedOnManualVehicle(
        PrivateMotorVehiclePage vehiclePage )
    {
        vehiclePage.setTypeOfCover( "Fire, Theft and Third Party Property Damage" );
        Assert.assertEquals( "$500", vehiclePage.getStandardExcess() );
        Assert.assertEquals( "", vehiclePage.getBasePremium() );
    }

}
