package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.CommentsGridRow;
import au.com.cgu.harvest.pages.CommentsPage;
import au.com.cgu.harvest.pages.ReferralAndErrorConditions;

@Activity( "Delete Comment in comments page" )
public class DeleteCommentActivity extends AbstractActivity< CommentsPage >
{
    private CommentsPage _page;
    private int _row;
    private String _comment;
    private String _type;
    private String _duration;
    private String _attachTo;

    public DeleteCommentActivity( CommentsPage page, int row )
    {
        _page = page;
        _row = row;
    }

    @Override
    protected CommentsPage execute()
    {
        ReferralAndErrorConditions navPanel = _page.getReferralAndErrorConditions();
        navPanel.getCountOfComments();

        CommentsGridRow commentsRow = _page.getCommentsPageGrid().getCommentAtRow( _row );
        commentsRow.deleteComment( true );

        return _page;
    }
}
