package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Exercise Error Rules for Countrypak Situations activity" )
public class ExerciseErrorRulesForSectionsActivity extends
    AbstractActivity< SituationDetailPage >
{
    private HarvestPage _page;

    public ExerciseErrorRulesForSectionsActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected SituationDetailPage execute()
    {
        SituationDetailPage addSituationLevelSectionPage =
            _page.getNavigationTree().navigateToSituation( 1 );

        addSituationLevelSectionPage.exerciseErrorRules();

        return addSituationLevelSectionPage;
    }
}
