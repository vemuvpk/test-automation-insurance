package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.RoadTransitPage;

@Activity( "Exercise Referral Rules for Countrypak Road Transit Page activity" )
public class ExerciseReferralRulesForRoadTransitPageActivity extends
    AbstractActivity< RoadTransitPage >
{
    private HarvestPage _page;

    public ExerciseReferralRulesForRoadTransitPageActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected RoadTransitPage execute()
    {

        assertReferralRuleForSumInsured( "$100,001", "R037" );

        return new RoadTransitPage( getWebDriver() );
    }

    private void assertReferralRuleForSumInsured( String value, String expectedRule )
    {
        RoadTransitPage roadTransitPage =
            _page.getNavigationTree().navigateToRoadTransit();

        roadTransitPage.setSumInsured( value );
        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( roadTransitPage.ROAD_TRANSIT_SUM_INSURED_LOCATOR ),
            expectedRule ) );

    }
}
