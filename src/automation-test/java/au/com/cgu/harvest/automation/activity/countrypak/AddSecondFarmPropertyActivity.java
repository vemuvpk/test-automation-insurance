package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Element;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Add farm Property 2 Activity" )
public class AddSecondFarmPropertyActivity extends AbstractActivity< FarmPropertyPage >
{
    private HarvestPage _page;

    public AddSecondFarmPropertyActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyPage farmPropertyPage =
            _page.getNavigationTree().navigateToFarmProperty( 2 );

        Assert.assertFalse( Element.isHidden( getWebDriver(), farmPropertyPage.EXCESS_LOCATOR ) );
        farmPropertyPage.setUnspecifiedFarmBuildings( "200" );
        farmPropertyPage.setAboveGroundFarmImprovements( "2000" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.FARM_PROPERTY, 2 );
        section.isTaken();

        return farmPropertyPage;
    }
}
