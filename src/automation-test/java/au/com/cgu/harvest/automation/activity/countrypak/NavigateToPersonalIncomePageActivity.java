package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;

@Activity( "Navigate to Personal Income Page in Countrypak" )
public class NavigateToPersonalIncomePageActivity extends
    AbstractActivity< PersonalIncomePage >

{
    private HarvestPage _page;

    public NavigateToPersonalIncomePageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PersonalIncomePage execute()
    {
        return _page.getNavigationTree().navigateToPersonalIncome( 1 );

    }
}
