package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Verify Error Message when Adding a Section in Deleted Situation in Countrypak" )
public class verifyErrorMessageWhenAddingSectionInDeletedSituation extends
    AbstractActivity< PolicyDetailPage >

{
    private HarvestPage _page;
    private WebDriver _driver;

    public verifyErrorMessageWhenAddingSectionInDeletedSituation( HarvestPage page, WebDriver driver )
    {
        _page = page;
        _driver = driver;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.HAY_FENCES_LIVESTOCK, 1 );
        section.click();

        Alert alert = _driver.switchTo().alert();
        String alertText = alert.getText().trim();
        AbstractScenario.getScenarioLogger().trace( alertText );
        Assert.assertTrue( alert.getText().matches(
            "You cannot add a section to a deleted situation" ) );
        alert.accept();

        return null;

    }
}
