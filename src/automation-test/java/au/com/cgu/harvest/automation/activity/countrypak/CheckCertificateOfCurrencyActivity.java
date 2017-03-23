package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Check Certificate of Currency Activity" )
public class CheckCertificateOfCurrencyActivity extends
    AbstractActivity< FinishPage >
{
    private HarvestPage _page;

    public CheckCertificateOfCurrencyActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FinishPage execute()
    {
        FinishPage finishPage =
            _page.getNavigationTree().navigateToFinish();
        finishPage.getCertificateofCurrency().getNumberOfRows();

        return finishPage;
    }
}
