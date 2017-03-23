package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.PolicyLevelDomesticBuildingPage;

@Activity( "Add Domestic Building at Policy Level with Manual Premium and check for Rules E020 and R062 Activity" )
public class AddPolicyLevelDomesticBuildingWithManualPremiumActivity extends
    AbstractActivity< PolicyLevelDomesticBuildingPage >
{
    private HarvestPage _page;

    public AddPolicyLevelDomesticBuildingWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyLevelDomesticBuildingPage execute()
    {
        PolicyLevelDomesticBuildingPage policyLevelDomesticBuildingPage =
            _page.getNavigationTree().navigateToPolicyLevelDomesticBuilding();

        policyLevelDomesticBuildingPage.setCoverType( "Listed Events" );
        policyLevelDomesticBuildingPage.setExcess( "500.000" );
        policyLevelDomesticBuildingPage.setUnspecifiedValuableSumInsured( "100" );

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( policyLevelDomesticBuildingPage.UNSPECIFIED_VALUABLES_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        policyLevelDomesticBuildingPage.setUnspecifiedValuableBasePremium( "55.55" );

        // Check for R062
        wait.until( Rule.isDisplayed(
            By.xpath( policyLevelDomesticBuildingPage.UNSPECIFIED_VALUABLES_BASE_PREMIUM_LOCATOR ),
            "R062" ) );

        policyLevelDomesticBuildingPage.getSpecifiedValuablesGrid().addRow( "Description-1",
            "5000" );
        policyLevelDomesticBuildingPage.getSpecifiedValuablesGrid().addRow( "Description-2",
            "5000" );
        wait.until( Rule.isDisplayed(
            By.xpath( policyLevelDomesticBuildingPage.SPECIFIED_VALUABLES_BASE_PREMIUM_LOCATOR ),
            "E020" ) );
        policyLevelDomesticBuildingPage.setSpecifiedValuableBasePremium( "55.55" );
        // Check for R062
        wait.until( Rule.isDisplayed(
            By.xpath( policyLevelDomesticBuildingPage.SPECIFIED_VALUABLES_BASE_PREMIUM_LOCATOR ),
            "R062" ) );

        return policyLevelDomesticBuildingPage;
    }
}
