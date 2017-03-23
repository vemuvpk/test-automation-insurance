package au.com.cgu.harvest.automation.activity.sunrise;

import org.joda.time.LocalDate;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Renew a policy from sunrise executive" )
public class RenewActivity extends AbstractActivity< PolicyDetailPage >
{
    private NewBusinessPage _page;

    public RenewActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _page.renew();
        _page.setStartDate( new LocalDate()
            .plusYears( 1 ).plusDays( 2 ).toString( "dd/MM/yyyy" ) );
        _page.setEndDate( new LocalDate()
            .plusYears( 8 ).plusDays( 2 ).toString( "dd/MM/yyyy" ) );
        PolicyDetailPage policyDetailPage = _page.renewRiskDetails();
        return policyDetailPage;

    }
}
