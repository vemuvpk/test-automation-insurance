package au.com.cgu.harvest.automation.scenario.farmmotor.premium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleWithManualPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumRow;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "To Check adjustment percentage for manually rated vehicle" )
public class AdjustmentPercentageToCheckForManuallyRatedVehicleScenario extends AbstractScenario
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
            performActivity( new CreatePrivateMotorVehicleWithManualPremiumActivity(
                insuranceHistoryPage ) );

        vehiclePage.getNavigationTree().navigateToFinish();
        // VERIFY (40.1) - Assert Adjustment percentage to the vehicle is Read Only and check the
        // values for manually rated vehicle

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        // ReadOnlyChecker.assertTextBoxesAreReadOnly( getWebDriver(), premiumPage );
    }

    private void assertPremiumValuesInRow( PremiumRow row, String basePremium, String fireLevy,
        String gst, String stampDuty, String adjPercentage, String total )
    {
        Assert.assertEquals( basePremium, row.getTechnicalPremium() );
        Assert.assertEquals( fireLevy, row.getFireLevy() );
        Assert.assertEquals( gst, row.getGst() );
        Assert.assertEquals( stampDuty, row.getStampDuty() );
        Assert.assertEquals( adjPercentage, row.getAdjustment() );
        Assert.assertEquals( total, row.getTotalPremium() );
    }
}
