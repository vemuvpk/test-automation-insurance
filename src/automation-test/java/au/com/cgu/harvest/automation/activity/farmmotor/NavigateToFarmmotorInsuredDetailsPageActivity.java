package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuredDetailsPage;

@Activity( "Navigate to Farm Motor Insured Details Page" )
public class NavigateToFarmmotorInsuredDetailsPageActivity extends
    AbstractActivity< InsuredDetailsPage >

{
    private HarvestPage _page;

    public NavigateToFarmmotorInsuredDetailsPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected InsuredDetailsPage execute()
    {
        return _page.getNavigationTree().navigateToFarmMotorInsuredDetails();

    }
}
