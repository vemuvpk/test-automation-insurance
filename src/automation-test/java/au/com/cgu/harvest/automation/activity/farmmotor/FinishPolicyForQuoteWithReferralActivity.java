package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish Policy for a Quote with Referrals" )
public class FinishPolicyForQuoteWithReferralActivity extends AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;

    public FinishPolicyForQuoteWithReferralActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.isReferralTextPresent();
        finishPage.setPrintedQuote( "Yes" );
        return finishPage.finish();
    }
}
