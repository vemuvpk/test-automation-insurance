package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Set Policy Declaration to Yes in finish Page Activity" )
public class SetPolicyDeclarationAndFinishFarmMotorActivity extends
    AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;

    public SetPolicyDeclarationAndFinishFarmMotorActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setPolicyDeclaration( "Yes" );
        return finishPage.finish();
    }
}
