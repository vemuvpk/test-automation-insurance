package au.com.cgu.harvest.automation.activity.countrypak;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Assert State Field in Situation Page is Read Only" )
public class AssertStateFieldInSituationIsReadOnlyActivity extends
    AbstractActivity< SituationDetailPage >
{

    private SituationDetailPage _page;
    private WebDriver _driver;

    public AssertStateFieldInSituationIsReadOnlyActivity( WebDriver driver, SituationDetailPage page )
    {
        _driver = driver;
        _page = page;
    }

    @Override
    protected SituationDetailPage execute()
    {
        List< WebElement > matchingElements;
        matchingElements =
            _driver
                .findElements( By
                    .xpath( _page.GARAGE_STATECODE_LOCATOR ) );

        for ( WebElement element : matchingElements )
        {
            boolean containsDisabledList =
                element.getAttribute( "class" ).contains( "ph-ListBox-disabled" );
            boolean containsNoList =
                element.getAttribute( "disabled" ).contains( "" );
            boolean containsDisabledDecorator =
                element.getAttribute( "class" ).contains( "ph-ListBoxDecorator-disabled" );
            Assert
                .assertTrue( containsDisabledDecorator || containsDisabledList || containsNoList );
        }

        return _page;
    }
}
