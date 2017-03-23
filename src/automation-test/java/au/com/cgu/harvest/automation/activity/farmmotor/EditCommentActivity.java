package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.CommentsPage;
import au.com.cgu.harvest.pages.CommentsPopup;

@Activity( "Edit Comment Activity" )
public class EditCommentActivity extends AbstractActivity< CommentsPopup >
{
    private CommentsPage _page;
    private int _row;

    public EditCommentActivity( CommentsPage page, int row )
    {
        _page = page;
        _row = row;
    }

    @Override
    protected CommentsPopup execute()
    {
        CommentsPopup editComment = _page.getCommentsPageGrid().getCommentAtRow( _row ).edit();

        return editComment;
    }
}
