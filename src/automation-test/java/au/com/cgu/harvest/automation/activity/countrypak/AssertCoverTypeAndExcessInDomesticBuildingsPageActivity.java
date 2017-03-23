package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;

@Activity( "Assert Cover Type and Excess in Domestic Buildings and Contents Page Activity" )
public class AssertCoverTypeAndExcessInDomesticBuildingsPageActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DomesticBuildingAndContentsPage _page;
    private String _excess;
    private String _coverType;

    public AssertCoverTypeAndExcessInDomesticBuildingsPageActivity(
        DomesticBuildingAndContentsPage page,
        String coverType, String excess )
    {
        _page = page;
        _coverType = coverType;
        _excess = excess;

    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        Assert.assertEquals( _coverType, _page.getCoverTypeValue() );
        Assert.assertEquals( _excess, _page.getExcessValue() );
        return new DomesticBuildingAndContentsPage( getWebDriver() );
    }
}
