package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Get Acceptance from sunrise executive" )
public class GetAcceptanceActivity extends AbstractActivity< NewBusinessPage >
{
    private NewBusinessPage _page;

    public GetAcceptanceActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _page.getAcceptance();
        return new NewBusinessPage( getWebDriver() );
    }
}
