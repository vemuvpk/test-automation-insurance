package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;

@Activity( "Add a Personal Income Activity" )
public class EditPersonalIncomeActivity extends
    AbstractActivity< PersonalIncomePage >
{
    private HarvestPage _page;

    public EditPersonalIncomeActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PersonalIncomePage execute()
    {
        PersonalIncomePage personalIncomePage =
            _page.getNavigationTree().navigateToPersonalIncome( 1 );

        return personalIncomePage;
    }
}
