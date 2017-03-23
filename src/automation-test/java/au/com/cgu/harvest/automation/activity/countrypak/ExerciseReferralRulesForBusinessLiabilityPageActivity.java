package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPageXpath;

@Activity( "Exercise Referral Rules for Countrypak Business Interruption Page activity" )
public class ExerciseReferralRulesForBusinessLiabilityPageActivity extends
    AbstractActivity< BusinessLiabilityPage >
{
    private HarvestPage _page;

    public ExerciseReferralRulesForBusinessLiabilityPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessLiabilityPage execute()
    {
        BusinessLiabilityPage businessLiabilityPage =
            _page.getNavigationTree().navigateToBusinessLiability();

        businessLiabilityPage.setLiabilitLimitOfIndemnity( "25000000.000" );
        _page.ruleTriggered( BusinessLiabilityPageXpath.LIABILITY_LIMIT_OF_INDEMNITY_LOCATOR,
            "R037" );

        businessLiabilityPage.setNumberOfSituationsThisCoverAppliesTo( "3" );
        _page.ruleTriggered( BusinessLiabilityPageXpath.SITUIATIONS_THIS_COVER_APPLY_TO_LOCATOR,
            "R002" );

        businessLiabilityPage.setPercentageIncomeDerived( "11" );
        _page.ruleTriggered(
            BusinessLiabilityPageXpath.PERCENTAGE_INCOME_FROM_CONTRACT_FARMING_LOCATOR,
            "R005" );

        businessLiabilityPage.setActualIncomeDerived( "50001" );
        _page.ruleTriggered(
            BusinessLiabilityPageXpath.ACTUAL_INCOME_FROM_CONTRACT_FARMING,
            "R006" );

        businessLiabilityPage.setIsPropertyLeased( "true" );
        _page.ruleTriggered(
            BusinessLiabilityPageXpath.IS_PROPERTY_LEASED,
            "R007" );

        return businessLiabilityPage;
    }
}
