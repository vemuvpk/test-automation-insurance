package au.com.cgu.harvest.automation.activity.farmmotor;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.ReprintDocumentsPage;

@Activity( "Select Documents that need to be Reprinted Activity" )
public class AssertDocumentsInListActivity extends AbstractActivity< ReprintDocumentsPage >
{
    private ReprintDocumentsPage _page;
    private WebDriver _driver;
    private ArrayList< String > _documents;

    public AssertDocumentsInListActivity( ReprintDocumentsPage page,
        ArrayList< String > print )
    {
        _page = page;
        _documents = print;
    }

    @Override
    protected ReprintDocumentsPage execute()
    {

        for ( String print : _documents )
        {
            _page.isDocumentPresent( print );
        }
        return null;
    }
}
