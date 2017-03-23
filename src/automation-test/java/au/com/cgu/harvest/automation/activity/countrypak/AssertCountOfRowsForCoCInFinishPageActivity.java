package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CPCertificateOfCurrencyGrid;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Assert count of Rows for Certificate of Currency in Finish page" )
public class AssertCountOfRowsForCoCInFinishPageActivity extends
    AbstractActivity< FinishPage >
{
    private HarvestPage _page;
    private int _count;

    public AssertCountOfRowsForCoCInFinishPageActivity( HarvestPage page, int count )
    {
        _page = page;
        _count = count;
    }

    @Override
    protected FinishPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        CPCertificateOfCurrencyGrid grid =
            finishPage.getCountrypakCertificateofCurrency();
        Assert.assertEquals( _count, grid.getNumberOfRows() );

        return finishPage;
    }
}
