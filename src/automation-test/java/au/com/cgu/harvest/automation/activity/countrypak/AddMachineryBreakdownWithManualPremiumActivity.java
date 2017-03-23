package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.countrypak.NameValueGridRow;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add Business Liability Page With all fields entered Activity" )
public class AddMachineryBreakdownWithManualPremiumActivity extends
    AbstractActivity< MachineryBreakdownPage >
{
    private HarvestPage _page;

    public AddMachineryBreakdownWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected MachineryBreakdownPage execute()
    {
        MachineryBreakdownPage machineryBreakdownPage =
            _page.getNavigationTree().navigateToMachineryBreakdown();

        machineryBreakdownPage.setBlanketCover( "true" );
        machineryBreakdownPage.setBlanketCoverRatingUnits( "2" );
        machineryBreakdownPage.setSpecifiedItemsRatingUnits( "3" );

        NameValueGridRow specifiedItemRow =
            machineryBreakdownPage.getSpecifiedItemsGrid().addRow();
        specifiedItemRow.setValues( "Description Name", "500" );
        specifiedItemRow =
            machineryBreakdownPage.getSpecifiedItemsGrid().addRow();
        specifiedItemRow.setValues( "Description Name-2", "1500" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.MACHINERY_BREAKDOWN );
        section.isTaken();

        // Check for E020
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( machineryBreakdownPage.BLANKET_COVER_BASE_PREMIUM ), "E020" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( machineryBreakdownPage.SPECIFIED_ITEMS_BASE_PREMIUM ), "E020" ) );

        machineryBreakdownPage.setBlanketCoverBasePremium( "55.55" );
        machineryBreakdownPage.setSpecifiedItemsBasePremium( "55.55" );

        // Check for R062
        wait.until( Rule.isDisplayed(
            By.xpath( machineryBreakdownPage.BLANKET_COVER_BASE_PREMIUM ), "R062" ) );
        wait.until( Rule.isDisplayed(
            By.xpath( machineryBreakdownPage.SPECIFIED_ITEMS_BASE_PREMIUM ), "R062" ) );

        return machineryBreakdownPage;
    }
}
