package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.CommentsPage;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.ReferralAndErrorConditions;

@Activity( "View comments" )
public class NavigateToCommentsActivity extends AbstractActivity< CommentsPage >
{
    private HarvestPage _page;

    public NavigateToCommentsActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected CommentsPage execute()
    {
        ReferralAndErrorConditions navPanel = _page.getReferralAndErrorConditions();

        CommentsPage commentsPage = _page.getReferralAndErrorConditions().clickOnComment();

        return commentsPage;
    }
}
