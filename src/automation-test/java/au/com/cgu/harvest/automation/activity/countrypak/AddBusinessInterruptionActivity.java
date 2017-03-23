package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add Business Interruption Page With all fields entered Activity" )
public class AddBusinessInterruptionActivity extends AbstractActivity< BusinessInterruptionPage >
{
    private HarvestPage _page;

    public AddBusinessInterruptionActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessInterruptionPage execute()
    {
        BusinessInterruptionPage businessInterruptionPage =
            _page.getNavigationTree().navigateToBusinessInterruption();

        businessInterruptionPage.setAgistmentIncomeSumInsured( "1000" );
        businessInterruptionPage.setFarmContinuationExpensesSumInsured( "500" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.BUSINESS_INTERRUPTION );
        section.isTaken();
        return businessInterruptionPage;
    }
}
