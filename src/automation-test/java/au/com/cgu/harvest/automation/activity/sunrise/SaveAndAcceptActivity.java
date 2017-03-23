package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Save and accept a policy from sunrise executive" )
public class SaveAndAcceptActivity extends AbstractActivity< NewBusinessPage >
{
    private NewBusinessPage _page;

    public SaveAndAcceptActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _page.save();
        _page.accept();

        return new NewBusinessPage( getWebDriver() );
    }
}
