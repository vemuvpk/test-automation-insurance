package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Error and Referral rules for Personal Income page Activity" )
public class ExerciseCalculationsForPersonalIncomeActivity
    extends
    AbstractActivity< PersonalIncomePage >
{
    private HarvestPage _page;

    public ExerciseCalculationsForPersonalIncomeActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PersonalIncomePage execute()
    {

        PersonalIncomePage personalIncomePage =
            _page.getNavigationTree().navigateToPersonalIncome( 1 );
        _page.getNavigationTree().getSection( SectionType.PERSONAL_INCOME, 1 );
        AbstractScenario.getScenarioLogger().trace( "Started Calculations" );

        personalIncomePage.setNumberOfAccidentUnits( "2" );
        Assert.assertEquals( "$10,000", personalIncomePage.getAccidentCapitalBenefit() );
        Assert.assertEquals( "$100", personalIncomePage.getAccidentWeeklyIncomeBenefit() );

        personalIncomePage.setNumberOfIllnessUnits( "3" );
        Assert.assertEquals( "$15,000", personalIncomePage.getIllnessCapitalBenefit() );
        Assert.assertEquals( "$150", personalIncomePage.getIllnessWeeklyIncomeBenefit() );

        AbstractScenario.getScenarioLogger().trace( "Finished calculations rules" );
        return personalIncomePage;
    }
}
