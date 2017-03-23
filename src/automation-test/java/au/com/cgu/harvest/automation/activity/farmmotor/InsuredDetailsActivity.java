package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuredDetailsPage;

@Activity( "Complete the insured details in a manner that allows a policy to be completed" )
public class InsuredDetailsActivity extends AbstractActivity< InsuredDetailsPage >
{
    private HarvestPage _page;

    public InsuredDetailsActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected InsuredDetailsPage execute()
    {
        InsuredDetailsPage insuredDetailsPage =
            _page.getNavigationTree().navigateToInsuredDetails();

        insuredDetailsPage.setSuburb( "Sydney" );
        insuredDetailsPage.setState( "NSW" );
        insuredDetailsPage.setPostcode( "2000" );
        return insuredDetailsPage;
    }
}
