package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Exercise Mandatory Rules for Countrypak Situations activity" )
public class ExerciseMandatoryRulesForSectionsActivity extends
    AbstractActivity< SituationDetailPage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForSectionsActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected SituationDetailPage execute()
    {
        SituationDetailPage addSituationLevelSectionPage =
            _page.getToolbar().addSituation();

        addSituationLevelSectionPage.exerciseMandatoryFields();

        return addSituationLevelSectionPage;
    }

}
