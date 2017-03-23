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
import au.com.cgu.harvest.pages.countrypak.SectionType;
import au.com.cgu.harvest.pages.countrypak.TheftPage;

@Activity( "Add Theft - farm Contents value, Farm contents and Farm Machinery Activity" )
public class AddTheftPageWithManualPremiumActivity extends AbstractActivity< TheftPage >
{
    private HarvestPage _page;

    public AddTheftPageWithManualPremiumActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected TheftPage execute()
    {
        TheftPage theftPage =
            _page.getNavigationTree().navigateToTheft();

        theftPage.setFarmContentsValue( "10,000" );
        theftPage.getSpecifiedFarmContentsGrid().addRow( "Farm content-1", "100" );
        theftPage.getSpecifiedFarmContentsGrid().addRow( "Farm content-2", "200" );
        theftPage.getSpecifiedFarmMachineryGrid().addRow( "Farm Machinery-1", "100" );
        theftPage.getSpecifiedFarmMachineryGrid().addRow( "Farm Machinery-2", "200" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.THEFT );
        section.isTaken();
        // Check for E020
        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed(
            By.xpath( theftPage.FARM_CONTENTS_AT_SITUATION_BASE_PREMIUM ), "E020" ) );

        theftPage.setFarmContentsAtSituationBasePremium( "55.55" );

        // Check for R062
        wait.until( Rule.isDisplayed(
            By.xpath( theftPage.FARM_CONTENTS_AT_SITUATION_BASE_PREMIUM ), "R062" ) );
        theftPage.setSpecifiedFarmContentsBasePremium( "55.55" );
        theftPage.setSpecifiedFarmMachineryBasePremium( "55.55" );
        return theftPage;
    }

}
