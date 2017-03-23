package au.com.cgu.harvest.automation.activity.sunrise;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Modify Risk Details and change End date to check for Error message for a policy from sunrise executive" )
public class ModifyRiskDetailsToChangeEndDateActivity extends AbstractActivity< NewBusinessPage >
{
    private NewBusinessPage _page;

    public ModifyRiskDetailsToChangeEndDateActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        _page.modify();
        _page.setEndDate( "18/07/2020" );
        _page.modifyRiskDetailsForError();
        Assert.assertEquals( "Ref: FAILED-E056 .", _page.getErrorRef() );
        AbstractScenario.getScenarioLogger().trace( "Error message is: " + _page.getErrorRef() );
        return _page;
    }
}
