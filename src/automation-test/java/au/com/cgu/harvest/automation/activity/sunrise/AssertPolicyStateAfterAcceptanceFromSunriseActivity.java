package au.com.cgu.harvest.automation.activity.sunrise;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Assert Policy State after Acceptance from sunrise executive" )
public class AssertPolicyStateAfterAcceptanceFromSunriseActivity extends
    AbstractActivity< NewBusinessPage >
{
    private NewBusinessPage _page;
    private String _policyState;

    public AssertPolicyStateAfterAcceptanceFromSunriseActivity( NewBusinessPage page,
        String policyState )
    {
        _page = page;
        _policyState = policyState;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _page.getAcceptedState();
        AbstractScenario.getScenarioLogger().trace( "Policy State is:" + _page.getAcceptedState() );
        Assert.assertEquals( _policyState, _page.getAcceptedState() );
        return _page;
    }
}
