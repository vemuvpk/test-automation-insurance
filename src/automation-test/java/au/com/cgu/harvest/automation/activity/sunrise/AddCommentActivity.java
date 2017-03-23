package au.com.cgu.harvest.automation.activity.sunrise;

import java.util.ArrayList;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.CommentsPopup;
import au.com.cgu.harvest.pages.HarvestPage;

@Activity( "Add Comment Activity" )
public class AddCommentActivity extends AbstractActivity< HarvestPage >
{
    private HarvestPage _page;
    private ArrayList< String > _sections;
    private String _comment;
    private String _type;
    private String _duration;

    public AddCommentActivity( HarvestPage page, ArrayList< String > sections, String comment,
        String type, String duration )
    {
        _page = page;
        _sections = sections;
        _comment = comment;
        _type = type;
        _duration = duration;
    }

    @Override
    protected HarvestPage execute()
    {
        CommentsPopup addCommentPage = _page.getToolbar().addComment();
        addCommentPage.checkOkDisabled();

        addCommentPage.setType( _type );
        addCommentPage.setDuration( _duration );
        for ( String sectionName : _sections )
        {
            addCommentPage.attachTo( sectionName );
        }
        addCommentPage.setComment( _comment );
        addCommentPage.ok();
        _page.getReferralAndErrorConditions().getCountOfComments();

        return _page;
    }
}
