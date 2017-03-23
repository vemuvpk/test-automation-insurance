package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;

@Activity( "Change Rating Factor and Assert that Premium value is Blanked out Activity" )
public class ChangeRatingFactorInPersonalIncomePageActivity extends
    AbstractActivity< PersonalIncomePage >
{
    private HarvestPage _page;

    public ChangeRatingFactorInPersonalIncomePageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PersonalIncomePage execute()
    {
        PersonalIncomePage personalIncomePage =
            _page.getNavigationTree().navigateToPersonalIncome( 1 );

        personalIncomePage.setNumberOfAccidentUnits( "4" );
        Assert.assertEquals( "", personalIncomePage.getAccidentBasePremiumValue() );
        personalIncomePage.setNumberOfIllnessUnits( "4" );
        Assert.assertEquals( "", personalIncomePage.getIllnessBasePremiumValue() );

        return personalIncomePage;
    }
}
