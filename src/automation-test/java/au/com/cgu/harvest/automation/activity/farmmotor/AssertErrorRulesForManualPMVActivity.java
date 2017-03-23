package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.PrivateMotorVehiclePageXpath;

@Activity( "Assert Error Rules For Manual PMV Activity" )
public class AssertErrorRulesForManualPMVActivity extends
    AbstractActivity< PrivateMotorVehiclePage >
{
    private PrivateMotorVehiclePage _page;

    public AssertErrorRulesForManualPMVActivity( PrivateMotorVehiclePage privateVehiclePage )
    {
        _page = privateVehiclePage;
    }

    @Override
    protected PrivateMotorVehiclePage execute()
    {
        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        wait.until( Rule.isDisplayed(
            By.xpath( PrivateMotorVehiclePageXpath.STANDARD_EXCESS ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( PrivateMotorVehiclePageXpath.TRANSACTION_BASE_PREMIUM ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( PrivateMotorVehiclePageXpath.WINDSCREEN_TRANSACTION_BASE_PREMIUM ),
            "E020" ) );

        return _page;

    }

}
