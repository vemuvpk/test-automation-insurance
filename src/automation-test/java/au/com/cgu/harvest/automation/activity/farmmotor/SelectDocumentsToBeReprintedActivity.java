package au.com.cgu.harvest.automation.activity.farmmotor;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.ReprintDocumentsPage;

@Activity( "Select Documents that need to be Reprinted Activity" )
public class SelectDocumentsToBeReprintedActivity extends AbstractActivity< ReprintDocumentsPage >
{
    private ReprintDocumentsPage _page;
    private WebDriver _driver;
    private ArrayList< String > _documents;

    public SelectDocumentsToBeReprintedActivity( ReprintDocumentsPage page,
        ArrayList< String > documentList )
    {
        _page = page;
        _documents = documentList;
    }

    @Override
    protected ReprintDocumentsPage execute()
    {

        for ( String documentList : _documents )
        {
            _page.setReprintDocument( documentList );
        }
        return _page;
    }
}
