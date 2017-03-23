package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Delete Farm Property from Nav Tree Activity" )
public class DeleteFarmPropertyFromNavTreeActivity extends AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;
    private int _position;

    public DeleteFarmPropertyFromNavTreeActivity( HarvestPage page, int position )
    {
        _page = page;
        _position = position;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        PolicyDetailPage farmPropertyPage =
            _page.getNavigationTree().deleteFarmPropertyAtPosition( _position );

        return farmPropertyPage;
    }
}
