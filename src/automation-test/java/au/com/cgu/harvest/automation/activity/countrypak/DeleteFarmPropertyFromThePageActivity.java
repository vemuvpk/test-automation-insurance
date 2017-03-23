package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Delete Farm Property from the page and assert the Green Tick is gone Activity" )
public class DeleteFarmPropertyFromThePageActivity extends AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyPage _page;

    public DeleteFarmPropertyFromThePageActivity( FarmPropertyPage farmPropertyPage )
    {
        _page = farmPropertyPage;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        _page.delete();
        return _page;

    }
}
