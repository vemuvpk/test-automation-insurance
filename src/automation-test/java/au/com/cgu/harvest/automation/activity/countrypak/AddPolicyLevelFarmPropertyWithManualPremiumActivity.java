package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelFarmPropertyPage;

@Activity( "Add farm Property Activity" )
public class AddPolicyLevelFarmPropertyWithManualPremiumActivity extends
    AbstractActivity< PolicyLevelFarmPropertyPage >
{
    private HarvestPage _page;

    public AddPolicyLevelFarmPropertyWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyLevelFarmPropertyPage execute()
    {
        PolicyLevelFarmPropertyPage policyLevelFarmPropertyPage =
            _page.getNavigationTree().navigateToPolicyLevelFarmProperty();

        policyLevelFarmPropertyPage.setCoverType( "Listed Events" );
        policyLevelFarmPropertyPage.setExcess( "500.000" );

        policyLevelFarmPropertyPage.getSpecifiedItemsGrid().addRow( "Description-1",
            "5000" );
        policyLevelFarmPropertyPage.getSpecifiedItemsGrid().addRow( "Description-2",
            "5000" );

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( policyLevelFarmPropertyPage.SPECIFIED_ITEMS_BASE_PREMIUM_LOCATOR ),
            "E020" ) );

        policyLevelFarmPropertyPage.setSpecifiedItemsBasePremium( "55.55" );

        // Check for R036
        wait.until( Rule.isDisplayed(
            By.xpath( policyLevelFarmPropertyPage.SPECIFIED_ITEMS_BASE_PREMIUM_LOCATOR ),
            "R062" ) );

        return policyLevelFarmPropertyPage;
    }
}
