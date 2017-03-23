package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPageXpath;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Exercise Error Rules for Countrypak Business Liability Page activity" )
public class ExerciseErrorRulesForBusinessLiabilityPageActivity extends
    AbstractActivity< BusinessLiabilityPage >
{
    private HarvestPage _page;

    public ExerciseErrorRulesForBusinessLiabilityPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessLiabilityPage execute()
    {
        BusinessLiabilityPage businessLiabilityPage =
            _page.getNavigationTree().navigateToBusinessLiability();

        _page.getNavigationTree().getSection( SectionType.BUSINESS_LIABILITY )
            .containsRule( "E006" );

        businessLiabilityPage.setNumberOfSituationsThisCoverAppliesTo( "Invalid String" );
        _page.ruleTriggered(
            BusinessLiabilityPageXpath.SITUIATIONS_THIS_COVER_APPLY_TO_LOCATOR, "E019" );

        businessLiabilityPage.setNumberOfWorkingEmployees( "Invalid String" );
        _page.ruleTriggered(
            BusinessLiabilityPageXpath.NUMBER_OF_EMPLOYEES_LOCATOR, "E019" );

        businessLiabilityPage.setNumberOfWorkingProprietors( "Invalid String" );
        _page.ruleTriggered(
            BusinessLiabilityPageXpath.NUMBER_OF_WORKING_PROPRIETORS_LOCATOR, "E019" );

        return businessLiabilityPage;
    }
}
