package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Save, Edit and Edit Risk Details from sunrise executive" )
public class SaveAndEditRiskDetailsActivity extends AbstractActivity< NewBusinessPage >
{
    private NewBusinessPage _page;

    public SaveAndEditRiskDetailsActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _page.save();
        _page.edit();
        return new NewBusinessPage( getWebDriver() );
    }
}
