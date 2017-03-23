package au.com.cgu.harvest.automation.scenario.farmmotor;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.farmmotor.CreateAgriculturalVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.CreatePrivateMotorVehicleActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.InsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFirstVehiclePageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchFarmMotorActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndAcceptActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.InsuranceHistoryPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePageXpath;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePageXpath;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Assert State Cannot be changed in an Endorsement Transaction for Farmmotor Policy Scenario" )
public class AssertCanChangeStateForFarmmotorEndorsementsScenario extends AbstractScenario
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

        PrivateMotorVehiclePage privateVehiclePage =
            performActivity( new CreatePrivateMotorVehicleActivity( insuranceHistoryPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity(
                insuranceHistoryPage ) );

        newBusinessPage =
            performActivity( new SaveAndAcceptActivity( newBusinessPage ) );
        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );

        privateVehiclePage =
            performActivity( new NavigateToFirstVehiclePageActivity( policyDetailPage ) );

        assertStateFieldIsNotReadOnly( privateVehiclePage );

        AgriculturalVehiclePage agriculturalVehicle =
            performActivity( new CreateAgriculturalVehicleActivity( privateVehiclePage ) );

        assertStateFieldIsNotReadOnly( agriculturalVehicle );

    }

    private void assertStateFieldIsNotReadOnly( AgriculturalVehiclePage agriculturalVehicle )
    {
        List< WebElement > matchingElements;
        matchingElements =
            getWebDriver()
                .findElements( By
                    .xpath( AgriculturalVehiclePageXpath.GARAGE_STATECODE ) );

        for ( WebElement element : matchingElements )
        {
            boolean containsDisabledList =
                element.getAttribute( "class" ).contains( "ph-ListBox-disabled" );
            boolean containsDisabledDecorator =
                element.getAttribute( "class" ).contains( "ph-ListBoxDecorator-disabled" );
            Assert
                .assertFalse( containsDisabledDecorator && containsDisabledList );
        }
    }

    private void assertStateFieldIsNotReadOnly( PrivateMotorVehiclePage privateVehiclePage )
    {
        List< WebElement > matchingElements;
        matchingElements =
            getWebDriver()
                .findElements( By
                    .xpath( PrivateMotorVehiclePageXpath.GARAGE_STATE_CODE ) );

        for ( WebElement element : matchingElements )
        {
            boolean containsDisabledList =
                element.getAttribute( "class" ).contains( "ph-ListBox-disabled" );
            boolean containsDisabledDecorator =
                element.getAttribute( "class" ).contains( "ph-ListBoxDecorator-disabled" );
            Assert
                .assertFalse( containsDisabledDecorator && containsDisabledList );
        }
    }

}
