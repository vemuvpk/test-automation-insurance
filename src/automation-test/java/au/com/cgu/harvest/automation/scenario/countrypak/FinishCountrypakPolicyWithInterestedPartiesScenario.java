package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddInterestedPartyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddInterestedPartyPolicyAllActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithFarmContentsAndFarmMachinery;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ViewInterestedPartiesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithTwoCertificateOfCurrencyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndAcceptActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Finish Countrypak Policy with multiple Interested parties and assert they are present in Finish Page scenario" )
public class FinishCountrypakPolicyWithInterestedPartiesScenario extends AbstractScenario
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

        SituationDetailPage situationLevelSectionPage =
            performActivity( new CreateASituationForSituationLevelSectionsActivity(
                insuranceHistoryPage ) );

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStockActivity( situationLevelSectionPage ) );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithFarmContentsAndFarmMachinery(
                hayFencesLiveStockPage ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( theftPage ) );

        InterestedPartiesPage interestedPartiesPage =
            performActivity( new ViewInterestedPartiesActivity( roadTransitPage ) );

        interestedPartiesPage =
            performActivity( new AddInterestedPartyActivity( interestedPartiesPage ) );
        interestedPartiesPage =
            performActivity( new AddInterestedPartyPolicyAllActivity( interestedPartiesPage ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyWithTwoCertificateOfCurrencyActivity(
                insuranceHistoryPage ) );

        newBusinessPage =
            performActivity( new SaveAndAcceptActivity( newBusinessPage ) );

    }
}
