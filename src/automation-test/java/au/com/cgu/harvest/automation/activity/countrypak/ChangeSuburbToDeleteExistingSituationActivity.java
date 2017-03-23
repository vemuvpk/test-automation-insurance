package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.JavascriptExecutor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Change Suburb to delete existing Situation and Create New Situation in Countrypak Endorsement Activity" )
public class ChangeSuburbToDeleteExistingSituationActivity extends
    AbstractActivity< SituationDetailPage >

{
    private SituationDetailPage _page;
    private int _situation;

    public ChangeSuburbToDeleteExistingSituationActivity( SituationDetailPage page )
    {
        _page = page;
    }

    @Override
    protected SituationDetailPage execute()
    {
        ( ( JavascriptExecutor ) getWebDriver() )
            .executeScript( "window.confirm = function(msg){return true;};" );
        _page.setSuburbStatePostcode( "Orange", "NSW", "2800" );
        return _page;

    }
}
