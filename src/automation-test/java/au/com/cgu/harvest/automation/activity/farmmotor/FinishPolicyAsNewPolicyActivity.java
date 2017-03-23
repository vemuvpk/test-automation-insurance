package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish Policy as a New policy With Intermediary Statement" )
public class FinishPolicyAsNewPolicyActivity extends AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;

    public FinishPolicyAsNewPolicyActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Policy" );
        finishPage.setPolicyDeclaration( "Yes" );
        finishPage.setPremiumPaymentMethod( "Intermediary Statement" );
        return finishPage.finish();
    }
}
