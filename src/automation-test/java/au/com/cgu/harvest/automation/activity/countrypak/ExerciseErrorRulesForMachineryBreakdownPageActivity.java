package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.MachineryBreakdownPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Exercise Error Rules for Countrypak Machinery Breakdown Page activity" )
public class ExerciseErrorRulesForMachineryBreakdownPageActivity extends
    AbstractActivity< MachineryBreakdownPage >
{
    private HarvestPage _page;

    public ExerciseErrorRulesForMachineryBreakdownPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected MachineryBreakdownPage execute()
    {
        MachineryBreakdownPage machineryBreakdownPage =
            _page.getNavigationTree().navigateToMachineryBreakdown();

        _page.getNavigationTree().getSection( SectionType.MACHINERY_BREAKDOWN )
            .containsRule( "E008" );

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );

        machineryBreakdownPage.setBlanketCover( "true" );
        machineryBreakdownPage.setBlanketCoverRatingUnits( "String" );
        wait.until( Rule.isDisplayed( By
            .xpath( machineryBreakdownPage.BLANKET_COVER_RATING_UNITS ), "E043" ) );

        machineryBreakdownPage.setSpecifiedItemsRatingUnits( "String" );
        wait.until( Rule.isDisplayed( By
            .xpath( machineryBreakdownPage.SPECIFIED_ITEMS_RATING_UNITS_LOCATOR ), "E043" ) );

        machineryBreakdownPage.setBlanketCoverRatingUnits( "10000" );
        wait.until( Rule.isDisplayed( By
            .xpath( machineryBreakdownPage.BLANKET_COVER_RATING_UNITS ), "E044" ) );

        machineryBreakdownPage.setSpecifiedItemsRatingUnits( "10000" );
        wait.until( Rule.isDisplayed( By
            .xpath( machineryBreakdownPage.SPECIFIED_ITEMS_RATING_UNITS_LOCATOR ), "E044" ) );

        machineryBreakdownPage.delete();
        return machineryBreakdownPage;
    }
}
