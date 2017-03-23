package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePageXpath;

@Activity( "Assert Referral For Manual PMV Page Activity" )
public class AssertReferralForManualPMVActivity extends AbstractActivity< PrivateMotorVehiclePage >
{
    private PrivateMotorVehiclePage _page;

    public AssertReferralForManualPMVActivity( PrivateMotorVehiclePage privateVehiclePage )
    {
        _page = privateVehiclePage;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        _page.setStandardExcess( "45" );
        _page.setTransactionBasePremium( "111.11" );
        _page.setTransactionWindscreenPremium( "44.44" );

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( PrivateMotorVehiclePageXpath.STANDARD_EXCESS ), "R036" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( PrivateMotorVehiclePageXpath.TRANSACTION_BASE_PREMIUM ), "R036" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( PrivateMotorVehiclePageXpath.WINDSCREEN_TRANSACTION_BASE_PREMIUM ),
            "R036" ) );

        return _page;

    }

}
