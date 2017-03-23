package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;

@Activity( "Exercise Mandatory Rules for Countrypak Machinery Breakdown Page activity" )
public class ExerciseMandatoryRulesForMachineryBreakdownPageActivity extends
    AbstractActivity< MachineryBreakdownPage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForMachineryBreakdownPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected MachineryBreakdownPage execute()
    {
        MachineryBreakdownPage machineryBreakdownPage =
            _page.getNavigationTree().navigateToMachineryBreakdown();
        machineryBreakdownPage.exerciseMandatoryRules();

        return machineryBreakdownPage;
    }
}
