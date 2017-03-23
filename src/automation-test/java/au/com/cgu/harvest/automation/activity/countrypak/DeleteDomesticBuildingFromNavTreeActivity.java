package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;

@Activity( "Delete Domestic Building from Nav Tree Activity" )
public class DeleteDomesticBuildingFromNavTreeActivity extends AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;
    private int _position;

    public DeleteDomesticBuildingFromNavTreeActivity( HarvestPage page, int position )
    {
        _page = page;
        _position = position;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        PolicyDetailPage farmPropertyPage =
            _page.getNavigationTree().deleteDomesticBuildingAtPosition( _position );

        return farmPropertyPage;
    }
}
