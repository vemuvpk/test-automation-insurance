package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Assert that finish button is disabled on Finish Page" )
public class AssertFinishPageFinishButtonDisabledActivity extends AbstractActivity< FinishPage >
{
    private HarvestPage _page;

    public AssertFinishPageFinishButtonDisabledActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FinishPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.isFinishDisabled();
        finishPage.isFinishDisabled();
        return finishPage;

    }

}
