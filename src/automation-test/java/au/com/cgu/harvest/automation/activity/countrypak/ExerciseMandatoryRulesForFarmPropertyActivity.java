package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Exercise Mandatory Rules for Domestic Buildings and Contents Activity" )
public class ExerciseMandatoryRulesForFarmPropertyActivity extends
    AbstractActivity< FarmPropertyPage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForFarmPropertyActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyPage execute()
    {

        FarmPropertyPage farmPropertyPage =
            _page.getNavigationTree().navigateToFarmProperty( 1 );
        _page.getNavigationTree().getSection( SectionType.FARM_PROPERTY, 1 )
            .containsRule( "E008" );
        farmPropertyPage.exerciseMandatoryRule();

        return farmPropertyPage;
    }
}
