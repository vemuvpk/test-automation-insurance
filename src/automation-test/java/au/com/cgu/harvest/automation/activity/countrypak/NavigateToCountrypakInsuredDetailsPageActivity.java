package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuredDetailsPage;

@Activity( "Navigate to Theft Page in Countrypak" )
public class NavigateToCountrypakInsuredDetailsPageActivity extends
    AbstractActivity< InsuredDetailsPage >

{
    private HarvestPage _page;

    public NavigateToCountrypakInsuredDetailsPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected InsuredDetailsPage execute()
    {
        return _page.getNavigationTree().navigateToCountrypakInsuredDetails();

    }
}
