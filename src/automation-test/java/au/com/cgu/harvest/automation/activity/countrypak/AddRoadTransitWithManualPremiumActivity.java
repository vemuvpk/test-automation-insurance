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
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add Road Transit - Sum Insured Activity" )
public class AddRoadTransitWithManualPremiumActivity extends AbstractActivity< RoadTransitPage >
{
    private HarvestPage _page;

    public AddRoadTransitWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected RoadTransitPage execute()
    {
        RoadTransitPage roadTransitPage =
            _page.getNavigationTree().navigateToRoadTransit();

        roadTransitPage.setRoadTransit();

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.ROAD_TRANSIT );
        section.isTaken();

        // Check for E020
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( roadTransitPage.BASE_PREMIUM ), "E020" ) );

        roadTransitPage.setBasePremium( "55.55" );

        // Check for R036
        wait.until( Rule.isDisplayed(
            By.xpath( roadTransitPage.BASE_PREMIUM ), "R062" ) );
        return roadTransitPage;
    }
}
