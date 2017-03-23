package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.AgriculturalVehiclePageXpath;

@Activity( "AgriculturalVehicle Assert Referral For TypeOfMachine Activity" )
public class AgriculturalVehicleAssertReferralRuleForTypeOfMachineActivity
    extends
    AbstractActivity< AgriculturalVehiclePage >
{
    private HarvestPage _page;

    public AgriculturalVehicleAssertReferralRuleForTypeOfMachineActivity(
        HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected AgriculturalVehiclePage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        AgriculturalVehiclePage vehicleDetailPage = addVehiclePage.addAgriculturalMotorVehicle();

        vehicleDetailPage.setVehicleValue( "100001" );
        vehicleDetailPage.setTypeOfMachine( "Header" );
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( AgriculturalVehiclePageXpath.TYPE_OF_MACHINE ),
            "R031" ) );

        return vehicleDetailPage;

    }

}
