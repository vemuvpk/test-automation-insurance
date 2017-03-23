package au.com.cgu.harvest.automation.activity.sunrise;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Assert Policy State from sunrise executive" )
public class AssertQuoteStateFromSunriseActivity extends AbstractActivity< NewBusinessPage >
{
    private NewBusinessPage _page;
    private String _quoteStatus;

    public AssertQuoteStateFromSunriseActivity( NewBusinessPage page, String quoteStatus )
    {
        _page = page;
        _quoteStatus = quoteStatus;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _page.getQuoteState();
        AbstractScenario.getScenarioLogger().trace( "Quote State is:" + _page.getQuoteState() );
        Assert.assertEquals( _quoteStatus, _page.getQuoteState() );
        return _page;
    }
}
