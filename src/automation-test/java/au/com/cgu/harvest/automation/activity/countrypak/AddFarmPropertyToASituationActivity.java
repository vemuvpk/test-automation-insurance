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
public class AddFarmPropertyToASituationActivity extends AbstractActivity< FarmPropertyPage >
{
    private HarvestPage _page;
    private int _farmProperty;

    public AddFarmPropertyToASituationActivity( HarvestPage page, int farmProperty )
    {
        _page = page;
        _farmProperty = farmProperty;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyPage farmPropertyPage =
            _page.getNavigationTree().navigateToFarmProperty( _farmProperty );

        Assert.assertFalse( Element.isHidden( getWebDriver(), farmPropertyPage.EXCESS_LOCATOR ) );
        farmPropertyPage.setUnspecifiedFarmBuildings( "200" );
        farmPropertyPage.setAboveGroundFarmImprovements( "2000" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.FARM_PROPERTY, _farmProperty );
        section.isTaken();

        return farmPropertyPage;
    }
}
