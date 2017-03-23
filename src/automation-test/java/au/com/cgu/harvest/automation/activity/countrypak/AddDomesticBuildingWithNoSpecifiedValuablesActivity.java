package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;

@Activity( "Add Domestic Building With no Specified Valuables (There is no specified valuables grid for second situation)Activity" )
public class AddDomesticBuildingWithNoSpecifiedValuablesActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DomesticBuildingAndContentsPage _page;

    public AddDomesticBuildingWithNoSpecifiedValuablesActivity( DomesticBuildingAndContentsPage page )
    {
        _page = page;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        _page.setAdditionalBusinessContentsSumInsured( "100" );
        return _page;
    }
}
