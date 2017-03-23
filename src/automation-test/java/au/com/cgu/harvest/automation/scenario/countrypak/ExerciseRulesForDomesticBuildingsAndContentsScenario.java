package au.com.cgu.harvest.automation.scenario.countrypak;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRuleForPositiveIntegerForAdditionalBusinessContentsActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertErrorRuleForPositiveIntegerForUnspecifiedValuablesActivity;
import au.com.cgu.harvest.automation.activity.countrypak.AssertReferralForInsuredSumExceedsLimitInNavTreeActivity;
import au.com.cgu.harvest.automation.activity.countrypak.ExerciseMandatoryRulesForDomesticBuildingsAndContentsActivity;
import au.com.cgu.harvest.automation.activity.farmmotor.PolicyDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchCountrypakActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@RunWith( ParallelScenarioRunner.class )
@Scenario( "Exercise Mandatory, Referral and Error/Declined Rules for Domestic Buildings and Contents" )
public class ExerciseRulesForDomesticBuildingsAndContentsScenario extends AbstractScenario
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

        // Mandatory Rules
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            performActivity( new ExerciseMandatoryRulesForDomesticBuildingsAndContentsActivity(
                policyDetailPage ) );

        // Referral Rules
        domesticBuildingAndContentsPage =
            performActivity( new
            AssertReferralForInsuredSumExceedsLimitInNavTreeActivity(
                domesticBuildingAndContentsPage ) );

        // Error Rules
        domesticBuildingAndContentsPage =
            performActivity( new AssertErrorRuleForPositiveIntegerForAdditionalBusinessContentsActivity(
                domesticBuildingAndContentsPage ) );

        domesticBuildingAndContentsPage =
            performActivity( new AssertErrorRuleForPositiveIntegerForUnspecifiedValuablesActivity(
                domesticBuildingAndContentsPage ) );
    }
}
