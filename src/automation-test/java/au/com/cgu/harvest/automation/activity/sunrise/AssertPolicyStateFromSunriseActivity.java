package au.com.cgu.harvest.automation.activity.sunrise;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Assert Policy State from sunrise executive" )
public class AssertPolicyStateFromSunriseActivity extends AbstractActivity< NewBusinessPage >
{
    private NewBusinessPage _page;
    private String _policyState;

    public AssertPolicyStateFromSunriseActivity( NewBusinessPage page, String policyState )
    {
        _page = page;
        _policyState = policyState;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _page.getPolicyState();
        AbstractScenario.getScenarioLogger().trace( "Policy State is:" + _page.getPolicyState() );
        Assert.assertEquals( _policyState, _page.getPolicyState() );
        return _page;
    }
}
