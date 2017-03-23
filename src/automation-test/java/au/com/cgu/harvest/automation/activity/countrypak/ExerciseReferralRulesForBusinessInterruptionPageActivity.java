package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPageXpath;

@Activity( "Exercise Referral Rules for Countrypak Business Interruption Page activity" )
public class ExerciseReferralRulesForBusinessInterruptionPageActivity extends
    AbstractActivity< BusinessInterruptionPage >
{
    private HarvestPage _page;

    public ExerciseReferralRulesForBusinessInterruptionPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessInterruptionPage execute()
    {
        BusinessInterruptionPage businessInterruptionPage =
            _page.getNavigationTree().navigateToBusinessInterruption();

        businessInterruptionPage.setAgistmentIncomeSumInsured( "200001" );
        _page.ruleTriggered( BusinessInterruptionPageXpath.AGISTMENT_INCOME_SUM_INSURED,
            "R037" );

        businessInterruptionPage.setFarmContinuationExpensesSumInsured( "200001" );
        _page.ruleTriggered(
            BusinessInterruptionPageXpath.FARM_CONTINUATION_EXPENSES_SUM_INSURED,
            "R037" );

        return businessInterruptionPage;
    }
}
