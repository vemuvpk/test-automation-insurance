package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.SectionType;
import au.com.cgu.harvest.pages.countrypak.TheftPage;

@Activity( "Add Theft with a Referral Activity" )
public class AddTheftPageWithAReferralActivity extends AbstractActivity< TheftPage >
{
    private HarvestPage _page;

    public AddTheftPageWithAReferralActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected TheftPage execute()
    {
        TheftPage theftPage =
            _page.getNavigationTree().navigateToTheft();

        theftPage.setFarmContentsValue( "6,500,000" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.THEFT );
        section.isTaken();
        return theftPage;
    }

}
