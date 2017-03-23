package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Convert Quote to a New Business from sunrise executive" )
public class ConvertToNewBusinessActivity extends AbstractActivity< PolicyDetailPage >
{
    private NewBusinessPage _page;

    public ConvertToNewBusinessActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _page.convertToNewBusiness();
        _page.convertRiskDetails();

        return new PolicyDetailPage( getWebDriver() );
    }
}
