package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.PrimitiveWait;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Renew a policy from sunrise executive" )
public class RenewPolicyActivity extends AbstractActivity< PolicyDetailPage >
{
    private NewBusinessPage _page;
    private String _startDate;
    private String _endDate;

    public RenewPolicyActivity( NewBusinessPage page, String startDate, String endDate )
    {
        _page = page;
        _startDate = startDate;
        _endDate = endDate;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _page.renew();
        _page.setStartDate( _startDate );
        PrimitiveWait.forMillis( 1000 );
        _page.setEndDate( _endDate );
        return _page.renewRiskDetails();
    }
}
