package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToPolicyLevelDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.AddAuthorisationCodeAndFinishActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithReferralActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelDomesticBuildingPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "PH-78 , PH-559, CFMC-366" )
@Scenario( "Assert Domestic Workers Compensation for a Domestic Building At Policy Level Scenario" )
public class AssertWorkersCompensationAtPolicyLevelForDomesticBuildingScenario extends
    AbstractScenario
{
    @Test
    public void execute()
    {
        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchCountrypakActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );

        CountrypakInsuranceHistoryPage insuranceHistoryPage =
            performActivity( new CountrypakInsuranceHistoryActivity( policyDetailPage ) );

        SituationDetailPage situationDetailPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                policyDetailPage ) );
        situationDetailPage.setSuburbStatePostcode( "Perth", "WA", "6000" );

        PolicyLevelDomesticBuildingPage domesticBuildingPage =
            performActivity( new AddPolicyLevelDomesticBuildingActivity( situationDetailPage ) );
        domesticBuildingPage.setUnspecifiedValuableSumInsured( "2500001" );

        domesticBuildingPage = assertWorkersCompensationIsVisible( situationDetailPage );

        OutstandingReferralsPopup referralPopup =
            performActivity( new FinishPolicyAsNewPolicyWithReferralActivity( domesticBuildingPage ) );
        NewBusinessPage newBusinessPage =
            performActivity( new AddAuthorisationCodeAndFinishActivity( referralPopup ) );

    }

    private PolicyLevelDomesticBuildingPage assertWorkersCompensationIsVisible(
        SituationDetailPage situationDetailPage )
    {
        PolicyLevelDomesticBuildingPage domesticBuildingPage;
        domesticBuildingPage =
            performActivity( new NavigateToPolicyLevelDomesticBuildingActivity( situationDetailPage ) );

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( domesticBuildingPage.DOMESTIC_WORKERS_COMPENSATION_LOCATOR ) ) );
        domesticBuildingPage.setWorkersCompensation( "true" );
        return domesticBuildingPage;
    }
}
