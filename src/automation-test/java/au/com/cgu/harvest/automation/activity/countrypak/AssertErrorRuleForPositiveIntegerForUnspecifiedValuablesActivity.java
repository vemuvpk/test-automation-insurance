package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Error rule For Domestic Buildings and Contents - For Positive Integer For Unspecified Valuables Sum Insured Activity" )
public class AssertErrorRuleForPositiveIntegerForUnspecifiedValuablesActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private HarvestPage _page;

    public AssertErrorRuleForPositiveIntegerForUnspecifiedValuablesActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            _page.getNavigationTree().navigateToDomesticBuilding( 1 );
        _page.getNavigationTree().getSection( SectionType.DOMESTIC_BUILDING, 1 );

        domesticBuildingAndContentsPage.setAdditionalBusinessContentsSumInsured( "mmm" );
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( domesticBuildingAndContentsPage.ADDITIONAL_BUSINESS_CONTENT_SUM_INSURED_LOCATOR ),
            "E060" ) );
        return domesticBuildingAndContentsPage;
    }
}
