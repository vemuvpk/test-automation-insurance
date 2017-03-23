package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.CommentsGridRow;
import au.com.cgu.harvest.pages.CommentsPage;
import au.com.cgu.harvest.pages.ReferralAndErrorConditions;

@Activity( "Verify the state of the comments page" )
public class VerifyCopyPolicyCommentActivity extends AbstractActivity< CommentsPage >
{
    private CommentsPage _page;
    private int _row;
    private String _comment;
    private String _type;
    private String _duration;
    private String _attachTo;

    public VerifyCopyPolicyCommentActivity( CommentsPage page, int row, String comment )
    {
        _page = page;
        _row = row;
        _comment = comment;
    }

    @Override
    protected CommentsPage execute()
    {
        ReferralAndErrorConditions navPanel = _page.getReferralAndErrorConditions();
        navPanel.getCountOfComments();

        CommentsGridRow commentsRow = _page.getCommentsPageGrid().getCommentAtRow( _row );
        commentsRow.copyPolicyHasValues( _comment, "Internal Comment", "Current Term",
            "Policy" );

        return _page;
    }
}
