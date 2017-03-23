package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;

@Activity( "Assert Error Rules For Manual Domestic Buildings and Contents Activity" )
public class AssertErrorRulesForManualDomesticBuildingActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DomesticBuildingAndContentsPage _page;

    public AssertErrorRulesForManualDomesticBuildingActivity(
        DomesticBuildingAndContentsPage page )
    {
        _page = page;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.ADDITIONAL_BUSINESS_CONTENTS_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        return new DomesticBuildingAndContentsPage( getWebDriver() );
    }
}
