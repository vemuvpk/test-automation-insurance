package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Modify policy from sunrise executive" )
public class ModifyPolicyActivity extends AbstractActivity< NewBusinessPage >
{
    private NewBusinessPage _page;

    public ModifyPolicyActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _page.modify();
        return new NewBusinessPage( getWebDriver() );

    }
}
