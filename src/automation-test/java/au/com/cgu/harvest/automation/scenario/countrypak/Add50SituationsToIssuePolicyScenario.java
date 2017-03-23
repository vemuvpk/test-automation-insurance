package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AddBuildingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessInterruptionActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddBusinessLiabilityActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDomesticBuildingToASitautionActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddDwellingType;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmMachineryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddFarmPropertyToASituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddHayFenceLiveStockToASituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddInterestedPartyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddMachineryBreakdownActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddMultipleSituationActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPersonalIncomeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelDomesticBuildingActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddPolicyLevelFarmPropertyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddRoadTransitActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddSecondInterestedPartyActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AddTheftPageWithFarmContentsAndFarmMachinery;
import au.com.cgu.harvest.automation.activity.countrypak.CountrypakInsuranceHistoryActivity;
import au.com.cgu.harvest.automation.activity.countrypak.CreateASituationForSituationLevelSectionsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ViewInterestedPartiesActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ModifyAndModifyRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndAcceptActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakInsuranceHistoryPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;
import au.com.cgu.harvest.pages.countrypak.InterestedPartiesPage;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelDomesticBuildingPage;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelFarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;
import au.com.cgu.harvest.pages.countrypak.TheftPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Ignore
@RunWith( ParallelScenarioRunner.class )
@Scenario( "Add a Countrypak and then Issue and accept a policy scenario" )
public class Add50SituationsToIssuePolicyScenario extends AbstractScenario
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

        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new AddDomesticBuildingActivity( situationLevelSectionPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );

        FarmPropertyPage farmPropertyPage =
            performActivity( new AddFarmPropertyActivity( situationLevelSectionPage ) );
        farmPropertyPage =
            performActivity( new AddBuildingType( farmPropertyPage ) );

        HayFencesLiveStockPage hayFencesLiveStockPage =
            performActivity( new AddHayFenceLiveStockActivity( farmPropertyPage ) );

        // For loop to create 50 situations
        for ( int i = 2; i <= 21; i++ )
        {
            situationLevelSectionPage =
                performActivity( new AddMultipleSituationActivity( hayFencesLiveStockPage,
                    "Situation - " + i ) );

            domesticBuildingAndContentsPage =
                performActivity( new AddDomesticBuildingToASitautionActivity(
                    situationLevelSectionPage, i ) );
            domesticBuildingAndContentsPage =
                performActivity( new AddDwellingType( domesticBuildingAndContentsPage ) );

            farmPropertyPage =
                performActivity( new AddFarmPropertyToASituationActivity( hayFencesLiveStockPage, i ) );
            farmPropertyPage =
                performActivity( new AddBuildingType( farmPropertyPage ) );

            hayFencesLiveStockPage =
                performActivity( new AddHayFenceLiveStockToASituationActivity(
                    hayFencesLiveStockPage, i ) );
        }
        //

        PolicyLevelDomesticBuildingPage policyLevelDomesticbuilding =
            performActivity( new AddPolicyLevelDomesticBuildingActivity( farmPropertyPage ) );

        PolicyLevelFarmPropertyPage policyLevelFarmProperty =
            performActivity( new AddPolicyLevelFarmPropertyActivity( policyLevelDomesticbuilding ) );
        FarmMachineryAndWorkingDogsPage farmMachineryPage =
            performActivity( new AddFarmMachineryActivity( policyLevelFarmProperty ) );

        TheftPage theftPage =
            performActivity( new AddTheftPageWithFarmContentsAndFarmMachinery(
                farmMachineryPage ) );

        BusinessInterruptionPage businessInterruptionPage =
            performActivity( new AddBusinessInterruptionActivity( theftPage ) );

        BusinessLiabilityPage businessLiabilityPage =
            performActivity( new AddBusinessLiabilityActivity( businessInterruptionPage ) );

        MachineryBreakdownPage machineryBreakdownPage =
            performActivity( new AddMachineryBreakdownActivity( businessLiabilityPage ) );

        PersonalIncomePage personalIncomePage =
            performActivity( new AddPersonalIncomeActivity( machineryBreakdownPage ) );
        personalIncomePage =
            performActivity( new AddPersonalIncomeActivity( personalIncomePage, "Second Person",
                true ) );

        RoadTransitPage roadTransitPage =
            performActivity( new AddRoadTransitActivity( machineryBreakdownPage ) );

        InterestedPartiesPage interestedPartiesPage =
            performActivity( new ViewInterestedPartiesActivity( roadTransitPage ) );

        interestedPartiesPage =
            performActivity( new AddInterestedPartyActivity( interestedPartiesPage ) );

        String machineryBreakdownSection = "Section 8 - Machinery Breakdown";
        String theftSection = "Section 4 - Theft";

        interestedPartiesPage =
            performActivity( new AddSecondInterestedPartyActivity( interestedPartiesPage,
                machineryBreakdownSection ) );

        NewBusinessPage newBusinessPage =
            performActivity( new FinishPolicyAsNewPolicyWithCertificateOfCurrencyActivity(
                insuranceHistoryPage ) );

        newBusinessPage =
            performActivity( new SaveAndAcceptActivity( newBusinessPage ) );

        String policyNumber = newBusinessPage.getPolicyNumber();

        policyDetailPage =
            performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );


    }
}
