package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Referral For Domestic Buildings and Contents - When the Sum Insured Exceeds the Authorised Limit Activity" )
public class AssertReferralForInsuredSumExceedsLimitInNavTreeActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private HarvestPage _page;

    public AssertReferralForInsuredSumExceedsLimitInNavTreeActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            _page.getNavigationTree().navigateToDomesticBuilding( 1 );

        domesticBuildingAndContentsPage.setAdditionalBusinessContentsSumInsured( "3000000" );
        _page.getNavigationTree().getSection( SectionType.DOMESTIC_BUILDING, 1 )
            .containsRule( "R040" );
        return domesticBuildingAndContentsPage;

    }
}
