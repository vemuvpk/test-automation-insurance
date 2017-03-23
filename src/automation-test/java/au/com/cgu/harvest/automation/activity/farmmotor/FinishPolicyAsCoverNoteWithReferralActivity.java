package au.com.cgu.harvest.automation.activity.farmmotor;

import org.joda.time.LocalDate;
import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.OutstandingReferralsPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Finish Policy as a Covernote With Referral Activity" )
public class FinishPolicyAsCoverNoteWithReferralActivity extends
    AbstractActivity< OutstandingReferralsPopup >
{
    private HarvestPage _page;

    public FinishPolicyAsCoverNoteWithReferralActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected OutstandingReferralsPopup execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Covernote" );
        Assert.assertEquals(
            new LocalDate().plusDays( 30 ).toString( "dd/MM/yyyy" ),
            finishPage.getCoverNoteExpiryDate() );
        finishPage.setPrintedDeclaration( "No" );
        return finishPage.finishWithReferral();
    }
}
