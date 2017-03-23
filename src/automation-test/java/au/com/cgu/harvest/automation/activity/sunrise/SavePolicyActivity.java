package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Save a policy from sunrise executive" )
public class SavePolicyActivity extends AbstractActivity< NewBusinessPage >
{
    private NewBusinessPage _page;

    public SavePolicyActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _page.save();
        return new NewBusinessPage( getWebDriver() );
    }

}
