package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.WebDriver;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.InstalmentPlanPopup;

@Activity( "View Policy Totals in Instalment plan Activity" )
public class VerifyPolicyTotalsInInstalmentPlanActivity extends
    AbstractActivity< InstalmentPlanPopup >
{
    private InstalmentPlanPopup _page;
    private String _policyTotals;
    private WebDriver Driver;

    public VerifyPolicyTotalsInInstalmentPlanActivity( InstalmentPlanPopup page, String policyTotals )
    {
        _page = page;
        _policyTotals = policyTotals;
    }

    @Override
    protected InstalmentPlanPopup execute()
    {
        _page.getTotalPremiumValue();
        _page.assertTotalPremiumValue( _policyTotals );
        return _page;
    }
}
