package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.RetryingElementLocator;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.farmmotor.AddVehiclePage;
import au.com.cgu.harvest.pages.farmmotor.NonArticulatedTrailersPage;
import au.com.cgu.harvest.pages.farmmotor.NonArticulatedTrailersPageXpath;

@Activity( "Exercise Referral Rules for Non Articulated Trailer activity" )
public class ExerciseReferralRulesForNonArticulatedTrailersActivity extends
    AbstractActivity< NonArticulatedTrailersPage >
{
    private HarvestPage _page;

    public ExerciseReferralRulesForNonArticulatedTrailersActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NonArticulatedTrailersPage execute()
    {
        AddVehiclePage addVehiclePage = _page.getToolbar().addVehicle();

        NonArticulatedTrailersPage vehicleDetailPage = addVehiclePage.addNonArticulatedTrailers();
        System.out.println( "*** Started exercising Referral Rules ***" );
        RetryingElementLocator.getTextBox( getWebDriver(), "Year of Birth of Driver",
        		NonArticulatedTrailersPageXpath.YEAR_OF_BIRTH, "1800" );
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( NonArticulatedTrailersPageXpath.YEAR_OF_BIRTH ),
            "E009" ) );

        RetryingElementLocator.getTextBox( getWebDriver(), "Year of Birth of Driver",
        		NonArticulatedTrailersPageXpath.YEAR_OF_BIRTH, "2010" );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( NonArticulatedTrailersPageXpath.YEAR_OF_BIRTH ),
            "E012" ) );

        RetryingElementLocator
            .getTextBox( getWebDriver(), "Vehicle value", NonArticulatedTrailersPageXpath.VEHICLE_VALUE,
                "500001" );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( NonArticulatedTrailersPageXpath.VEHICLE_VALUE ), "D003" ) );

        RetryingElementLocator
            .getListBox( getWebDriver(), "Does vehicle operate outside the Base?",
            		NonArticulatedTrailersPageXpath.DOES_VEHICLE_OPERATE_OUTSIDE_BASE, "true" );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( NonArticulatedTrailersPageXpath.DOES_VEHICLE_OPERATE_OUTSIDE_BASE ),
            "R022" ) );

        RetryingElementLocator
            .getTextBox( getWebDriver(), "Year of Manufacture",
            		NonArticulatedTrailersPageXpath.YEAR_OF_MANUFACTURE, "1800" );
        RetryingElementLocator.getListBox( getWebDriver(), "IS Vehicle Registered",
        		NonArticulatedTrailersPageXpath.IS_VEHICLE_REGISTERED, "false" );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( NonArticulatedTrailersPageXpath.YEAR_OF_MANUFACTURE ), "R015" ) );

        RetryingElementLocator
        .getTextBox( getWebDriver(), "Year of Manufacture",
        		NonArticulatedTrailersPageXpath.YEAR_OF_MANUFACTURE, "1978" );
    RetryingElementLocator.getListBox( getWebDriver(), "IS Vehicle Registered",
    		NonArticulatedTrailersPageXpath.IS_VEHICLE_REGISTERED, "true" );
    wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
    wait.until( Rule.isDisplayed(
        By.xpath( NonArticulatedTrailersPageXpath.YEAR_OF_MANUFACTURE ), "R015" ) );

        RetryingElementLocator
            .getListBox( getWebDriver(), "Existing Unrepaired Damage ",
            		NonArticulatedTrailersPageXpath.EXISTING_UNREPAIRED_DAMAGE, "true" );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( NonArticulatedTrailersPageXpath.EXISTING_UNREPAIRED_DAMAGE ), "R021" ) );

        RetryingElementLocator
            .getTextBox( getWebDriver(), "Vehicle value",
            		NonArticulatedTrailersPageXpath.VEHICLE_VALUE, "150001" );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( NonArticulatedTrailersPageXpath.VEHICLE_VALUE ), "R003" ) );

        RetryingElementLocator
            .getListBox( getWebDriver(), "At Fault Claim Free Years",
            		NonArticulatedTrailersPageXpath.AT_FAULT_CLAIM_FREE_YEAR, "4 years" );
        RetryingElementLocator
            .getListBox( getWebDriver(), "Obtained Proof for At fault Claim Free years",
            		NonArticulatedTrailersPageXpath.OBTAINED_PROOF_OF_CLAIM_YEAR, "false" );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( NonArticulatedTrailersPageXpath.OBTAINED_PROOF_OF_CLAIM_YEAR ), "R020" ) );

        RetryingElementLocator
            .getTextBox( getWebDriver(), "Garage PostCode",
            		NonArticulatedTrailersPageXpath.GARAGE_POSTCODE, "2000" );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( NonArticulatedTrailersPageXpath.GARAGE_POSTCODE ), "R008" ) );

        RetryingElementLocator.getListBox( getWebDriver(), "IS Vehicle Registered",
        		NonArticulatedTrailersPageXpath.IS_VEHICLE_REGISTERED, "true" );
        RetryingElementLocator.getListBox( getWebDriver(), "Limit of Liability",
        		NonArticulatedTrailersPageXpath.LIMIT_OF_LIABILITY, "40000000.000" );
        wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( NonArticulatedTrailersPageXpath.LIMIT_OF_LIABILITY ),
            "R004" ) );

        return vehicleDetailPage;

    }
}
