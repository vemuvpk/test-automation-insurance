package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.HayFencesLiveStockPage;

@Activity( "Add Hay Fence and Live Stock Activity" )
public class AddHayFenceLiveStockWithManualPremiumActivity extends
    AbstractActivity< HayFencesLiveStockPage >
{
    private HarvestPage _page;

    public AddHayFenceLiveStockWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected HayFencesLiveStockPage execute()
    {
        HayFencesLiveStockPage hayFencesLiveStockPage =
            _page.getNavigationTree().navigateToHayFenceLiveStock( 1 );

        hayFencesLiveStockPage.setBoundarySharedSumInsured( "1000" );
        hayFencesLiveStockPage.setBoundaryNotSharedSumInsured( "10000" );
        hayFencesLiveStockPage.setAllFencingSumInsured( "1000" );
        hayFencesLiveStockPage.setSheepSumInsured( "500" );
        hayFencesLiveStockPage.setHayGrainSectionTotalSumInsured( "1000" );
        hayFencesLiveStockPage.setFarmTreeSectionTotalSumInsured( "1000" );

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.BOUNDARY_NOT_SHARED_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.BOUNDARY_SHARED_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.ALL_OTHER_FENCING_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.SHEEP_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.HAY_GRAIN_BASE_PREMIUM_LOCAOTR ),
            "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.FARM_TREES_BASE_PREMIUM_LOCATOR ),
            "E020" ) );

        hayFencesLiveStockPage.setBoundaryNotSharedBasePremium( "55.55" );
        hayFencesLiveStockPage.setBoundarySharedBasePremium( "55.55" );
        hayFencesLiveStockPage.setAllOtherFencingBasePremium( "55.55" );
        hayFencesLiveStockPage.setSheepBasePremium( "55.55" );
        hayFencesLiveStockPage.setHayGrainBasePremium( "55.55" );
        hayFencesLiveStockPage.setFarmTreesBasePremium( "55.55" );
        // Check for R062
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.BOUNDARY_NOT_SHARED_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.BOUNDARY_SHARED_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.ALL_OTHER_FENCING_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.SHEEP_BASE_PREMIUM_LOCATOR ),
            "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.HAY_GRAIN_BASE_PREMIUM_LOCAOTR ),
            "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( hayFencesLiveStockPage.FARM_TREES_BASE_PREMIUM_LOCATOR ),
            "R062" ) );

        return hayFencesLiveStockPage;
    }
}
