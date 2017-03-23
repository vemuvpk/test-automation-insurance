package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;

@Activity( "Assert Error Rules For Manual Hay Fences Activity" )
public class AssertErrorRulesForManualHayFencesActivity extends
    AbstractActivity< HayFencesLiveStockPage >
{
    private HayFencesLiveStockPage _page;

    public AssertErrorRulesForManualHayFencesActivity(
        HayFencesLiveStockPage page )
    {
        _page = page;
    }

    @Override
    protected HayFencesLiveStockPage execute()
    {

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.BOUNDARY_NOT_SHARED_TRANSACTION_BASE_PREMIUM ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.BOUNDARY_SHARED_BASE_PREMIUM ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.ALL_OTHER_FENCING_BASE_PREMIUM ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.SHEEP_TRANSACTION_BASE_PREMIUM ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.HAY_AND_GRAIN_BASE_PREMIUM ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.FARM_TREE_BASE_PREMIUM ), "E020" ) );
        return new HayFencesLiveStockPage( getWebDriver() );

    }
}
