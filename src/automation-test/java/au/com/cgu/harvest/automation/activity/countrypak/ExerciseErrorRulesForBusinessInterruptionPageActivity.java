package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Exercise Error Rules for Countrypak Business Interruption Page activity" )
public class ExerciseErrorRulesForBusinessInterruptionPageActivity extends
    AbstractActivity< BusinessInterruptionPage >
{
    private HarvestPage _page;

    public ExerciseErrorRulesForBusinessInterruptionPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessInterruptionPage execute()
    {
        BusinessInterruptionPage businessInterruptionPage =
            _page.getNavigationTree().navigateToBusinessInterruption();

        _page.getNavigationTree().getSection( SectionType.BUSINESS_INTERRUPTION )
            .containsRule( "E008" );

        return businessInterruptionPage;
    }
}
