package au.com.cgu.harvest.automation.scenario.countrypak;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToDomesticBuildingPageActivity;
import au.com.cgu.harvest.automation.activity.countrypak.NavigateToSituationActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.JiraReference;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.Element;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@JiraReference( "PH-78 , PH-559" )
@Scenario( "Assert Domestic Workers Compensation for a Domestic Building Scenario" )
public class AssertDomesticWorkersCompensationScenario extends
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

        DomesticBuildingAndContentsPage domesticBuildingPage =
            performActivity( new AddDomesticBuildingActivity( situationDetailPage ) );
        domesticBuildingPage =
            performActivity( new AddDwellingType( domesticBuildingPage ) );

        situationDetailPage =
            performActivity( new NavigateToSituationActivity( domesticBuildingPage, 1 ) );
        situationDetailPage.setSuburbStatePostcode( "Perth", "WA", "6000" );

        domesticBuildingPage = assertWorkersCompensationIsNotPresent( situationDetailPage );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyActivity( domesticBuildingPage ) );

    }

    private DomesticBuildingAndContentsPage assertWorkersCompensationIsNotPresent(
        SituationDetailPage situationDetailPage )
    {
        DomesticBuildingAndContentsPage domesticBuildingPage;
        domesticBuildingPage =
            performActivity( new NavigateToDomesticBuildingPageActivity( situationDetailPage, 1 ) );

        assertTrue( Element.isHidden( getWebDriver(),
            By.xpath( domesticBuildingPage.DOMESTIC_WORKERS_COMPENSATION_LOCATOR ) ) );

        return domesticBuildingPage;
    }
}
