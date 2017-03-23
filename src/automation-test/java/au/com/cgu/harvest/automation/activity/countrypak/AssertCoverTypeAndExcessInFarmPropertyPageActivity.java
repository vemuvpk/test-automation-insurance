package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Assert Cover Type and Excess in Farm Property Page Activity" )
public class AssertCoverTypeAndExcessInFarmPropertyPageActivity extends
    AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyPage _page;
    private String _excess;
    private String _coverType;

    public AssertCoverTypeAndExcessInFarmPropertyPageActivity( FarmPropertyPage page,
        String coverType, String excess )
    {
        _page = page;
        _coverType = coverType;
        _excess = excess;

    }

    @Override
    protected FarmPropertyPage execute()
    {
        Assert.assertEquals( _coverType, _page.getCoverTypeValue() );
        Assert.assertEquals( _excess, _page.getExcessValue() );
        return new FarmPropertyPage( getWebDriver() );
    }
}
