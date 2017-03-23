package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.WebDriver;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add Domestic Building Activity" )
public class AddSecondDomesticBuildingActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private HarvestPage _page;
    private WebDriver driver;

    public AddSecondDomesticBuildingActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            _page.getNavigationTree().navigateToDomesticBuilding( 2 );

        // assertFalse( Element.isHidden( getWebDriver(),
        // domesticBuildingAndContentsPage.COVER_TYPE_LOCATOR ) );
        // assertFalse( Element.isHidden( getWebDriver(),
        // domesticBuildingAndContentsPage.EXCESS_LOCATOR ) );
        // assertTrue( Element.isHidden( getWebDriver(),
        // domesticBuildingAndContentsPage.UNSPECIFIED_VALUE_SUM_INSURED_LOCATOR ) );

        domesticBuildingAndContentsPage.setAdditionalBusinessContentsSumInsured( "100" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.DOMESTIC_BUILDING, 2 );
        section.isTaken();

        return domesticBuildingAndContentsPage;

    }
}
