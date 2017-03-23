package au.com.cgu.harvest.automation.activity.farmmotor;

import org.joda.time.LocalDate;
import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish Policy as a Covernote for Farm Motor" )
public class FinishPolicyAsCoverNoteActivity extends AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;

    public FinishPolicyAsCoverNoteActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Covernote" );
        Assert.assertEquals( "Covernote Expiry Date",
            new LocalDate().plusDays( 30 ).toString( "dd/MM/yyyy" ),
            finishPage.getCoverNoteExpiryDate() );
        finishPage.setPrintedDeclaration( "Yes" );
        return finishPage.finish();
    }
}
