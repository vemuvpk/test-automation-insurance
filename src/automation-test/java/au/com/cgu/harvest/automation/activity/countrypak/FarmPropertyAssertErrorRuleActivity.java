package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Error rule For Domestic Buildings and Contents - For Positive Integer For Additional Business Contents SumInsured Activity" )
public class FarmPropertyAssertErrorRuleActivity
    extends
    AbstractActivity< FarmPropertyPage >
{
    private HarvestPage _page;

    public FarmPropertyAssertErrorRuleActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyPage farmPropertyPage =
            _page.getNavigationTree().navigateToFarmProperty( 1 );
        _page.getNavigationTree().getSection( SectionType.FARM_PROPERTY, 1 );

        farmPropertyPage.setAllFarmContentsLimit( "Invalid String" );
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.FARM_CONTENTS_LIMIT_LOCATOR ),
            "E060" ) );

        farmPropertyPage.setAboveGroundFarmImprovements( "Invalid String" );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.ABOVE_GROUND_FARM_IMPROVEMENTS_LOCATOR ),
            "E060" ) );

        farmPropertyPage.setAllFarmContentsLimit( "1000" );
        farmPropertyPage.setInAnyOneBuilding( "2000" );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.FARM_CONTENTS_LIMIT_LOCATOR ),
            "E027" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( farmPropertyPage.IN_ANY_ONE_BUILDING_LOCATOR ),
            "E027" ) );

        return farmPropertyPage;

    }
}
