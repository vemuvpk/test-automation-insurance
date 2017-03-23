package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.ReprintDocumentsPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Reprint Documents activity to see the list of options available to print" )
public class PrintPolicyActivity extends AbstractActivity< ReprintDocumentsPage >
{
    private NewBusinessPage _page;

    public PrintPolicyActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected ReprintDocumentsPage execute()
    {
        _page.print();
        return new ReprintDocumentsPage( getWebDriver() );
    }

}
