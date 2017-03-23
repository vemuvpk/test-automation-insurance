package au.com.cgu.harvest.automation.activity.sunrise;

import org.joda.time.LocalDate;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Modify Risk Details and change Attachment Date for a policy from sunrise executive" )
public class ModifyRiskDetailsToChangeAttachmentDateActivity extends
    AbstractActivity< PolicyDetailPage >
{
    private NewBusinessPage _page;

    public ModifyRiskDetailsToChangeAttachmentDateActivity( NewBusinessPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        _page.modify();
        _page.setAttachmentDate( new LocalDate().minusMonths( 2 )
            .toString( "dd/MM/yyyy" ) );
        return _page.modifyRiskDetails();
    }
}
