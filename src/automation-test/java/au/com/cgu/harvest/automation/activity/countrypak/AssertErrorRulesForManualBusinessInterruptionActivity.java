package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Rule;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPage;
import au.com.cgu.harvest.pages.countrypak.BusinessInterruptionPageXpath;

@Activity( "Assert Error Rules For Manual Business Interruption Page Activity" )
public class AssertErrorRulesForManualBusinessInterruptionActivity extends
    AbstractActivity< BusinessInterruptionPage >
{
    private BusinessInterruptionPage _page;

    public AssertErrorRulesForManualBusinessInterruptionActivity(
        BusinessInterruptionPage page )
    {
        _page = page;
    }

    @Override
    protected BusinessInterruptionPage execute()
    {

        WebDriverWait wait = new WebDriverWait( getWebDriver(), Rule.MAX_WAIT_SECS );

        _page.ruleTriggered( BusinessInterruptionPageXpath.AGISTMENT_INCOME_BASE_PREMIUM,
            "E020" );
        _page.ruleTriggered( BusinessInterruptionPageXpath.FARM_CONTINUATION_EXPENSES_BASE_PREMIUM,
            "E020" );

        return new BusinessInterruptionPage( getWebDriver() );

    }
}
