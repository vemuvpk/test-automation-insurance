package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Finish as a new policy" )
public class CompleteDetailsInFinishPageActivity extends AbstractActivity< FinishPage >
{
    private HarvestPage _page;

    public CompleteDetailsInFinishPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FinishPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Policy" );
        finishPage.setPolicyDeclaration( "Yes" );
        finishPage.setPremiumPaymentMethod( "Intermediary Statement" );
        return finishPage;
    }
}
