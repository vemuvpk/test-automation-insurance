package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Set Policy Declaraation to NO And Finish Countrypak Activity" )
public class SetPolicyDeclarationAndFinishCountrypakActivity extends
    AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;

    public SetPolicyDeclarationAndFinishCountrypakActivity( HarvestPage page )
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
