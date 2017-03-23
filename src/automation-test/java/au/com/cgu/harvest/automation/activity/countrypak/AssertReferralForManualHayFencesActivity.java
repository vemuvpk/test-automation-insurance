package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;

@Activity( "Assert Referral For Manual Hay Fences Activity" )
public class AssertReferralForManualHayFencesActivity extends
    AbstractActivity< HayFencesLiveStockPage >
{
    private HayFencesLiveStockPage _page;

    public AssertReferralForManualHayFencesActivity(
        HayFencesLiveStockPage page )
    {
        _page = page;
    }

    @Override
    protected HayFencesLiveStockPage execute()
    {
        _page.setBoundaryNotSharedTransactionBasePremium( "111.11" );
        _page.setBoundarySharedTransactionBasePremium( "111.11" );
        _page.setAllOtherFencingTransactionBasePremium( "111.11" );
        _page.setSheepTransactionBasePremium( "111.11" );
        _page.setHayGrainTransactionBasePremium( "111.11" );
        _page.setFarmTreesTransactionBasePremium( "111.11" );

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( _page.BOUNDARY_NOT_SHARED_TRANSACTION_BASE_PREMIUM ), "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.BOUNDARY_SHARED_BASE_PREMIUM ), "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.ALL_OTHER_FENCING_BASE_PREMIUM ), "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.SHEEP_TRANSACTION_BASE_PREMIUM ), "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.HAY_AND_GRAIN_BASE_PREMIUM ), "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( _page.FARM_TREE_BASE_PREMIUM ), "R062" ) );
        return new HayFencesLiveStockPage( getWebDriver() );

    }
}
