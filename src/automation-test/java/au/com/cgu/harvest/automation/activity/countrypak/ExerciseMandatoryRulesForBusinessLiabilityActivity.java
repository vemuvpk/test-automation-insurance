package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPageXpath;

@Activity( "Exercise Mandatory Rules for Countrypak Business Liability Page activity" )
public class ExerciseMandatoryRulesForBusinessLiabilityActivity extends
    AbstractActivity< BusinessLiabilityPage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForBusinessLiabilityActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessLiabilityPage execute()
    {
        BusinessLiabilityPage businessLiabilityPage =
            _page.getNavigationTree().navigateToBusinessLiability();
        AbstractScenario.getScenarioLogger().trace( "*** Started exercising Mandatory fields ***" );

        businessLiabilityPage
            .mandatoryErrorDisplayedFor( BusinessLiabilityPageXpath.LIABILITY_LIMIT_OF_INDEMNITY_LOCATOR );
        businessLiabilityPage
            .mandatoryErrorDisplayedFor( BusinessLiabilityPageXpath.SITUIATIONS_THIS_COVER_APPLY_TO_LOCATOR );
        businessLiabilityPage
            .mandatoryErrorDisplayedFor( BusinessLiabilityPageXpath.AIRCRAFT_LANDING_EXTENSION_REQUIRED_LOCATOR );
        businessLiabilityPage
            .mandatoryErrorDisplayedFor( BusinessLiabilityPageXpath.NUMBER_OF_WORKING_PROPRIETORS_LOCATOR );
        businessLiabilityPage
            .mandatoryErrorDisplayedFor( BusinessLiabilityPageXpath.NUMBER_OF_EMPLOYEES_LOCATOR );
        businessLiabilityPage
            .mandatoryErrorDisplayedFor( BusinessLiabilityPageXpath.DERIVE_INCOME_FROM_CONTRACT_FARMING_LOCATOR );
        businessLiabilityPage.setIsIncomeDerived( "true" );
        businessLiabilityPage
            .mandatoryErrorDisplayedFor( BusinessLiabilityPageXpath.IS_PROPERTY_LEASED );
        businessLiabilityPage
            .mandatoryErrorDisplayedFor( BusinessLiabilityPageXpath.PERCENTAGE_INCOME_FROM_CONTRACT_FARMING_LOCATOR );
        businessLiabilityPage
            .mandatoryErrorDisplayedFor( BusinessLiabilityPageXpath.ACTUAL_INCOME_FROM_CONTRACT_FARMING );

        AbstractScenario.getScenarioLogger().trace( "*** Finished exercising Mandatory fields ***" );

        return businessLiabilityPage;
    }

}
