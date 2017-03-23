package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.NavigationTree;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.Rule;

@Activity( "Assert error message E053 oon Policy Details In Navigation Tree when last section is deleted for Countrypak Situations activity" )
public class AssertErrorMessageWhenlastSectionIsDeleted extends
    AbstractActivity< PolicyDetailPage >
{
    private HarvestPage _page;

    public AssertErrorMessageWhenlastSectionIsDeleted( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected PolicyDetailPage execute()
    {
        NavigationTree navTree = _page.getNavigationTree();

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );
        wait.until( Rule.isDisplayed( By.xpath( navTree.POLICY_DETAIL_WITH_REFERRAL_LOCATOR ),
            "E053" ) );

        return new PolicyDetailPage( getWebDriver() );
    }
}
