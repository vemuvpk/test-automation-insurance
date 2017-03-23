package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;

@Activity( "Exercise Mandatory Rules for Domestic Buildings and Contents Activity" )
public class ExerciseMandatoryRulesForDomesticBuildingsAndContentsActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForDomesticBuildingsAndContentsActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {

        DomesticBuildingAndContentsPage addDomesticBuildingPage =
            _page.getNavigationTree().navigateToDomesticBuilding( 1 );
        addDomesticBuildingPage.exerciseMandatoryRule();

        return addDomesticBuildingPage;
    }
}
