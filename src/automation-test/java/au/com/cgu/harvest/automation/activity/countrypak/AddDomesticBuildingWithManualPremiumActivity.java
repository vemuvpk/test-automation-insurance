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
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add Domestic Building with Manual Premium and check for Rules E020 and R062 Activity" )
public class AddDomesticBuildingWithManualPremiumActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private HarvestPage _page;

    public AddDomesticBuildingWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage =
            _page.getNavigationTree().navigateToDomesticBuilding( 1 );

        domesticBuildingAndContentsPage.setCoverType( "Listed Events" );
        domesticBuildingAndContentsPage.setExcess( "500.000" );
        domesticBuildingAndContentsPage.setAdditionalBusinessContentsSumInsured( "100" );

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule
            .isDisplayed(
                By.xpath( domesticBuildingAndContentsPage.ADDITIONAL_BUSINESS_CONTENTS_BASE_PREMIUM_LOCATOR ),
                "E020" ) );

        domesticBuildingAndContentsPage.setAdditionalBusinessContentsBasePremium( "55.55" );

        // Check for R062
        wait.until( Rule
            .isDisplayed(
                By.xpath( domesticBuildingAndContentsPage.ADDITIONAL_BUSINESS_CONTENTS_BASE_PREMIUM_LOCATOR ),
                "R062" ) );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.DOMESTIC_BUILDING, 1 );
        section.isTaken();

        return domesticBuildingAndContentsPage;
    }
}
