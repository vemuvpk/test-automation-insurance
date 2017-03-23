package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.CopyPolicyPopup;
import au.com.cgu.harvest.pages.HarvestPage;

@Activity( "Copy Policy Activity" )
public class CopyPolicyActivity extends AbstractActivity< CopyPolicyPopup >
{
    private HarvestPage _page;

    public CopyPolicyActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected CopyPolicyPopup execute()
    {
        return _page.getToolbar().copyPolicy();
    }
}
