package au.com.cgu.harvest.automation.scenario.farmmotor.endorsements;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.GetCountOfRisksInTransactionPremiumActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.EditPrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.ViewPremiumActivity;
import au.com.cgu.harvest.automation.activity.sunrise.GetAcceptanceActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Premium;
import au.com.cgu.harvest.pages.PremiumAssert;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumRow;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Delete WindScreen Cover for Farm Motor vehicle and assert taht it is shown as deleted cover in the premium Sections as part of an Endorsement Scenario" )
public class DeleteWindScreenCoverAsPartOfAnFMEndorsementScenario extends
    AbstractScenario
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
            performActivity( new CreatePrivateMotorVehicleActivity(
                insuranceHistoryPage ) );
        vehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity(
                insuranceHistoryPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( vehiclePage ) );

        newBusinessPage =
            performActivity( new GetAcceptanceActivity( newBusinessPage ) );

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        PremiumPage premiumPage = performActivity( new ViewPremiumActivity( vehiclePage ) );

        verifyPremiumDecreasesAfterDeletingWindScreenCoverInEndorsement( premiumPage );
        assertStrikeThroughForDeletedWindScreenCoverInPremiumSections( premiumPage );

        premiumPage =
            performActivity( new GetCountOfRisksInTransactionPremiumActivity( premiumPage, 2 ) );

    }

    private void assertStrikeThroughForDeletedWindScreenCoverInPremiumSections(
        PremiumPage premiumPage )
    {
        premiumPage = performActivity( new ViewPremiumActivity( premiumPage ) );
        PremiumRow parentRow = premiumPage.getPremiumGrid().getPremiumAtRow( 2 );
        parentRow.toggleExpanded();
        PremiumRow childRow = parentRow.getChildRow( 2 );
        Assert.assertTrue( childRow.isChildRowDeleted() );
    }

    private void verifyPremiumDecreasesAfterDeletingWindScreenCoverInEndorsement(
        PremiumPage premiumPage )
    {
        PrivateMotorVehiclePage vehiclePage;
        Premium transactionPremiumEndorsement = premiumPage.getPolicyPremium();
        vehiclePage = performActivity( new EditPrivateMotorVehicleActivity( premiumPage, 2 ) );
        vehiclePage.setWindScreenExtension( "false" );

        PremiumPage transactionPremium =
            performActivity( new ViewPremiumActivity( vehiclePage ) );
        Premium premiumAfterVehicleDeleted = premiumPage.getPolicyPremium();

        PremiumAssert.assertTotalPremiumDecreased( transactionPremiumEndorsement,
            premiumAfterVehicleDeleted );
    }
}
