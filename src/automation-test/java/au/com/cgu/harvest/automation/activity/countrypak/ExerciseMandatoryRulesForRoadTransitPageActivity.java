package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.MandatoryFieldError;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;

@Activity( "Exercise Mandatory Rules for Countrypak Road Transit Page activity" )
public class ExerciseMandatoryRulesForRoadTransitPageActivity extends
    AbstractActivity< RoadTransitPage >
{
    private HarvestPage _page;

    public ExerciseMandatoryRulesForRoadTransitPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected RoadTransitPage execute()
    {
        RoadTransitPage roadTransitPage =
            _page.getNavigationTree().navigateToRoadTransit();
        AbstractScenario.getScenarioLogger().trace(
            "Started exercising Mandatory fields for Road Transit Page" );

        Wait< WebDriver > wait =
            new WebDriverWait( getWebDriver(), MandatoryFieldError.MAX_WAIT_SECS );
        wait.until( MandatoryFieldError.isDisplayed( By
            .xpath( roadTransitPage.ROAD_TRANSIT_SUM_INSURED_LOCATOR ) ) );

        AbstractScenario.getScenarioLogger().trace(
            "Finished exercising Mandatory fields for Road Transit Page" );

        return roadTransitPage;
    }
}
