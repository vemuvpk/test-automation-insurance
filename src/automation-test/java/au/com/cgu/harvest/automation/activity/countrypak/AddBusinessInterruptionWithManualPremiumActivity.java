package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPageXpath;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add Business Interruption Page With all fields entered Activity" )
public class AddBusinessInterruptionWithManualPremiumActivity extends
    AbstractActivity< BusinessInterruptionPage >
{
    private HarvestPage _page;

    public AddBusinessInterruptionWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessInterruptionPage execute()
    {
        BusinessInterruptionPage businessInterruptionPage =
            _page.getNavigationTree().navigateToBusinessInterruption();

        businessInterruptionPage.setAgistmentIncomeSumInsured( "1000" );
        businessInterruptionPage.setFarmContinuationExpensesSumInsured( "500" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.BUSINESS_INTERRUPTION );
        section.isTaken();

        businessInterruptionPage.ruleTriggered(
            BusinessInterruptionPageXpath.AGISTMENT_INCOME_BASE_PREMIUM,
            "E020" );
        businessInterruptionPage.ruleTriggered(
            BusinessInterruptionPageXpath.FARM_CONTINUATION_EXPENSES_BASE_PREMIUM,
            "E020" );

        businessInterruptionPage.setAgistmentBasePremium( "55.55" );

        // Check for R062
        businessInterruptionPage.ruleTriggered(
            BusinessInterruptionPageXpath.AGISTMENT_INCOME_BASE_PREMIUM,
            "R062" );

        businessInterruptionPage.setFarmContinuationBasePremium( "55.55" );
        businessInterruptionPage.ruleTriggered(
            BusinessInterruptionPageXpath.FARM_CONTINUATION_EXPENSES_BASE_PREMIUM,
            "R062" );
        return businessInterruptionPage;
    }
}
