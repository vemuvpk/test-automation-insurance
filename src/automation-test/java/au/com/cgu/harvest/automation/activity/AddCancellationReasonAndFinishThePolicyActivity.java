package au.com.cgu.harvest.automation.activity;

import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Save Transaction as New Policy with MOnthly Credit Card and Set Intermediary fees in FinishPage Activity" )
public class AddCancellationReasonAndFinishThePolicyActivity extends
    AbstractActivity< NewBusinessPage >
{
    private PremiumPage _page;

    public AddCancellationReasonAndFinishThePolicyActivity( PremiumPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _page.setCancellationReason( "Insured Elsewhere" );

        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        return finishPage.finish();
    }
}
