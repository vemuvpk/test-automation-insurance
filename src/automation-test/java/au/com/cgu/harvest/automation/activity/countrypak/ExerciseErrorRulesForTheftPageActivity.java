package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.SectionType;
import au.com.cgu.harvest.pages.countrypak.TheftPage;

@Activity( "Exercise Error Rules for Countrypak Business Liability Page activity" )
public class ExerciseErrorRulesForTheftPageActivity extends
    AbstractActivity< TheftPage >
{
    private HarvestPage _page;

    public ExerciseErrorRulesForTheftPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected TheftPage execute()
    {
        TheftPage theftPage =
            _page.getNavigationTree().navigateToTheft();

        _page.getNavigationTree().getSection( SectionType.THEFT )
            .containsRule( "E008" );

        theftPage.setFarmContentsValue( "Invalid String" );
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( theftPage.FARM_CONTENTS_VALUE_LOCATOR ), "E060" ) );

        return theftPage;
    }
}
