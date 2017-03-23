package au.com.cgu.harvest.automation.activity.farmmotor;


import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InsuredDetailsPage;

@Activity( "Exercise Rules for Farm Motor Insured Details Page activity" )
public class ExerciseRulesForFarmmotorInsuredDetailsPageActivity extends
    AbstractActivity< InsuredDetailsPage >
{
    private HarvestPage _page;

    public ExerciseRulesForFarmmotorInsuredDetailsPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected InsuredDetailsPage execute()
    {
        InsuredDetailsPage insuredDetailsPage =
            _page.getNavigationTree().navigateToInsuredDetails();
        insuredDetailsPage.setChangeMailingAddress( "Client" );
        Assert.assertEquals( "PO Box 300", insuredDetailsPage.getAddressLine1() );

        insuredDetailsPage.setChangeMailingAddress( "Intermediary" );
        Assert.assertEquals( "388 George St", insuredDetailsPage.getAddressLine1() );

        insuredDetailsPage.setChangeMailingAddress( "Other" );
        Assert.assertEquals( "", insuredDetailsPage.getAddressLine1() );
        Assert.assertEquals( "", insuredDetailsPage.getSuburb() );
        Assert.assertEquals( "", insuredDetailsPage.getPostcode() );

        return insuredDetailsPage;
    }
}
