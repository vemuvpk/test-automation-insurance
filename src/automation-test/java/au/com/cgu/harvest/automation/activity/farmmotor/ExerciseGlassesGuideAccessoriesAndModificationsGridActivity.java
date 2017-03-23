package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.Element;
import au.com.cgu.harvest.pages.FooterRow;
import au.com.cgu.harvest.pages.GlassesGuideAccessoryRow;
import au.com.cgu.harvest.pages.GlassesGuideModificationsRow;
import au.com.cgu.harvest.pages.farmmotor.GlassesGuideAfterMarketAccessoriesGrid;
import au.com.cgu.harvest.pages.farmmotor.GlassesGuideAfterMarketAccessoriesGridXpath;
import au.com.cgu.harvest.pages.farmmotor.GlassesGuideAfterMarketModificationsGrid;
import au.com.cgu.harvest.pages.farmmotor.GlassesGuideAfterMarketModificationsGridXpath;
import au.com.cgu.harvest.pages.farmmotor.IPageWithAccessoriesAndModificationsForGlassesGuide;

@Activity( "Exercise Glasses Guide Accessories and Modifications Grid Activity" )
public class ExerciseGlassesGuideAccessoriesAndModificationsGridActivity< PAGE extends IPageWithAccessoriesAndModificationsForGlassesGuide >
    extends AbstractActivity< PAGE >
{
    private PAGE _page;

    public ExerciseGlassesGuideAccessoriesAndModificationsGridActivity( PAGE vehicleDetailPage )
    {
        _page = vehicleDetailPage;
    }

    @Override
    protected PAGE execute()
    {
        AbstractScenario.getScenarioLogger().trace(
            "*** Exercising Accessories and Modifications Grids For Glasses Guide Vehicles ***" );
        GlassesGuideAfterMarketAccessoriesGrid accessories =
            _page.getAfterMarketAccessories();
        accessories.setHasAfterMarketAccessories( "true" );

        GlassesGuideAccessoryRow accessoryRow = accessories.getAccessoryAtRow( 1 );
        accessoryRow.setValues( "Body Kit", "Accessory Name", "500" );
        accessories.addAccessory( "Canopy", "Accessory Value", "1000" );
        accessories.addAccessory( "Gauges", "Accessory Name delete", "500" );
        accessories.getNumberOfRows();

        FooterRow footerRow = accessories.getFooterRow();
        assertEquals( "$2,000", footerRow.getElementAtColumn( 3 ).getText() );

        accessories.deleteAfterMarketAccessories( 3 );

        footerRow = accessories.getFooterRow();
        assertEquals( "$1,500", footerRow.getElementAtColumn( 3 ).getText() );

        accessories.setHasAfterMarketAccessories( "false" );
        assertTrue( Element.isHidden( getWebDriver(),
            GlassesGuideAfterMarketAccessoriesGridXpath.ACCESSORY_GRID_TABLE ) );
        accessories.setHasAfterMarketAccessories( "true" );
        assertEquals( "$1,500", accessories.getTotalAccessoryValue() );

        _page.setHasAfterMarketModifications( "true" );
        GlassesGuideAfterMarketModificationsGrid modifications =
            _page.getAfterMarketModifications();

        GlassesGuideModificationsRow modificationRow = modifications.getModificationAtRow( 1 );
        modificationRow.setValues( "Shaker", "Modification Name", "1500" );
        modifications.addModification( "Powerchip", "Modification name again", "100" );
        modifications.addModification( "Other", "Modification Name delete", "1500" );
        assertEquals( "$3,100", modifications.getTotalModificationsValue() );
        modifications.getNumberOfRows();
        modifications.deleteAfterMarketModifications( 3 );
        assertEquals( "$1,600", modifications.getTotalModificationsValue() );
        _page.setHasAfterMarketModifications( "false" );
        assertTrue( Element.isHidden( getWebDriver(),
            GlassesGuideAfterMarketModificationsGridXpath.MODIFICATIONS_GRID_TABLE ) );
        _page.setHasAfterMarketModifications( "true" );
        assertEquals( "$1,600", modifications.getTotalModificationsValue() );
        assertEquals( "$3,100", accessories.getTotalAccessoriesAndModificationsValue() );

        AbstractScenario.getScenarioLogger().trace(
            "*** Finished Exercising Accessories and Modifications Grids ***" );
        return _page;
    }
}
