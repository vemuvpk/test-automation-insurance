package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Import Risk Details for a policy from sunrise executive" )
public class ImportRiskDetailsActivity extends AbstractActivity< PolicyDetailPage >
{
    private NewBusinessPage _page;

    public ImportRiskDetailsActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _page.setClient( "SELENIUM" );
        _page.setInsured( "SELENIUM" );
        _page.renewRiskDetails();
        return new PolicyDetailPage( getWebDriver() );
    }
}
