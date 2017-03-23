package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;

@Activity( "Edit and Add new Premium Values to Business Liability Activity" )
public class EditManualPremiumValuesInPersonalIncomePageActivity extends
    AbstractActivity< PersonalIncomePage >
{
    private HarvestPage _page;

    public EditManualPremiumValuesInPersonalIncomePageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PersonalIncomePage execute()
    {
        PersonalIncomePage personalIncomePage =
            _page.getNavigationTree().navigateToPersonalIncome( 1 );

        personalIncomePage.setAccidentBasePremium( "66.66" );
        personalIncomePage.setIllnessBasePremium( "66.66" );

        return personalIncomePage;
    }
}
