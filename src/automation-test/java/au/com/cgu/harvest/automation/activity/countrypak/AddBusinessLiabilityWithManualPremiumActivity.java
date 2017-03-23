package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPage;
import au.com.cgu.harvest.pages.countrypak.BusinessLiabilityPageXpath;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add Business Liability Page With all fields entered Activity" )
public class AddBusinessLiabilityWithManualPremiumActivity extends
    AbstractActivity< BusinessLiabilityPage >
{
    private HarvestPage _page;

    public AddBusinessLiabilityWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessLiabilityPage execute()
    {
        BusinessLiabilityPage businessLiabilityPage =
            _page.getNavigationTree().navigateToBusinessLiability();

        businessLiabilityPage.setLiabilitLimitOfIndemnity( "5000000.000" );
        businessLiabilityPage.setNumberOfSituationsThisCoverAppliesTo( "2" );
        businessLiabilityPage.setIsAircraftLandingExtensionRequired( "false" );
        businessLiabilityPage.setNumberOfWorkingProprietors( "2" );
        businessLiabilityPage.setNumberOfWorkingEmployees( "2" );
        businessLiabilityPage.setIsIncomeDerived( "false" );
        businessLiabilityPage.setIsPropertyLeased( "false" );

        CountrypakSection section =
            businessLiabilityPage.getNavigationTree().getSection( SectionType.BUSINESS_LIABILITY );

        section.isTaken();

        businessLiabilityPage.ruleTriggered(
            BusinessLiabilityPageXpath.BASE_PREMIUM, "E020" );

        businessLiabilityPage.setBasePremiumValue( "55.55" );

        // Check for R062
        businessLiabilityPage.ruleTriggered(
            BusinessLiabilityPageXpath.BASE_PREMIUM,
            "R062" );

        return businessLiabilityPage;
    }
}
