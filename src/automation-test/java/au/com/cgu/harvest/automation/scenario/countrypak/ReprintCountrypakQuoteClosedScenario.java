package au.com.cgu.harvest.automation.scenario.countrypak;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyForQuoteActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.NavigateToFinishPageActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PrintPolicyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.SelectDocumentsToBeReprintedActivity;
import au.com.cgu.harvest.automation.activity.sunrise.AssertQuoteStateFromSunriseActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakQuoteActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SavePolicyActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.ReprintDocumentsPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Check Quote documents are available for reprint Scenario" )
public class ReprintCountrypakQuoteClosedScenario extends AbstractScenario
{
    @Test
    public void execute()
    {

        WelcomePage welcomePage =
            performActivity( new LoginToExecutiveActivity() );

        PolicyDetailPage policyDetailPage =
            performActivity( new LaunchCountrypakQuoteActivity( welcomePage ) );

        policyDetailPage =
            performActivity( new PolicyDetailsActivity( policyDetailPage ) );
        policyDetailPage.getCountrypakQuoteNumber();

        CountrypakInsuranceHistoryPage insuranceHistoryPage =
            performActivity( new CountrypakInsuranceHistoryActivity( policyDetailPage ) );

        SituationDetailPage situationLevelSectionPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                insuranceHistoryPage ) );

        DomesticBuildingAndContentsPage domesticBuildingspage =
            performActivity( new AddDomesticBuildingActivity( insuranceHistoryPage ) );
        domesticBuildingspage =
            performActivity( new AddDwellingType( domesticBuildingspage ) );

        FinishPage finishPage =
            performActivity( new NavigateToFinishPageActivity( policyDetailPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyForQuoteActivity( policyDetailPage ) );
        newBusinessPage =
            performActivity( new SavePolicyActivity( newBusinessPage ) );

        newBusinessPage =
            performActivity( new AssertQuoteStateFromSunriseActivity( newBusinessPage, "Complete" ) );

        ReprintDocumentsPage printPage =
            performActivity( new PrintPolicyActivity( newBusinessPage ) );
        ArrayList< String > documentList = new ArrayList< String >();
        documentList.add( "Quote Schedule" );
        printPage =
            performActivity( new SelectDocumentsToBeReprintedActivity( printPage, documentList ) );

    }

}
