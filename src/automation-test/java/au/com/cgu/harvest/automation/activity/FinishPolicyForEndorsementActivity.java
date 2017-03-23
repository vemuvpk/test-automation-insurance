package au.com.cgu.harvest.automation.activity;

import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish the Endorsement Activity" )
public class FinishPolicyForEndorsementActivity extends AbstractActivity< NewBusinessPage >
{
    private FinishPage _page;

    public FinishPolicyForEndorsementActivity( FinishPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _page.finish();
        return new NewBusinessPage( getWebDriver() );

    }

}
