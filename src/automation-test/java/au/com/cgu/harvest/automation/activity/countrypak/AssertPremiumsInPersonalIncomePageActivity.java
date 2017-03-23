package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.DodgyAssert;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.PersonalIncomePage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Premium values in Personal Income Page Activity" )
public class AssertPremiumsInPersonalIncomePageActivity extends
    AbstractActivity< PersonalIncomePage >
{
    private HarvestPage _page;
    private String _accidentBasePremium;
    private String _illnessBasePremium;
    private String _sectionTotalPremium;

    public AssertPremiumsInPersonalIncomePageActivity( HarvestPage page,
        String accidentBasePremium, String illnessBasePremium, String sectionTotalPremium )
    {
        _page = page;
        _accidentBasePremium = accidentBasePremium;
        _illnessBasePremium = illnessBasePremium;
        _sectionTotalPremium = sectionTotalPremium;
    }

    @Override
    protected PersonalIncomePage execute()
    {
        PersonalIncomePage personalIncomePage =
            _page.getNavigationTree().navigateToPersonalIncome( 1 );

        DodgyAssert.assertNearlyEquals( _accidentBasePremium,
            personalIncomePage.getAccidentBasePremiumValue() );
        DodgyAssert.assertNearlyEquals( _illnessBasePremium,
            personalIncomePage.getIllnessBasePremiumValue() );
        DodgyAssert.assertNearlyEquals( _sectionTotalPremium,
            personalIncomePage.getSectionTotalBasePremiumValue() );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.PERSONAL_INCOME, 1 );
        section.isTaken();

        return personalIncomePage;
    }
}
