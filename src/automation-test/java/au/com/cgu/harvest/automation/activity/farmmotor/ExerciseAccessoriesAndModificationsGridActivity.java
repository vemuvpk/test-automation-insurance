package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.AccessoryRow;
import au.com.cgu.harvest.pages.Element;
import au.com.cgu.harvest.pages.ModificationRow;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketAccessoriesGrid;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketModificationsGrid;
import au.com.cgu.harvest.pages.farmmotor.IPageWithAccessoriesAndModifications;

@Activity( "Exercise Accessories and Modifications Grid Activity" )
public class ExerciseAccessoriesAndModificationsGridActivity< PAGE extends IPageWithAccessoriesAndModifications >
    extends AbstractActivity< PAGE >
{
    private PAGE _page;

    public ExerciseAccessoriesAndModificationsGridActivity( PAGE vehicleDetailPage )
    {
        _page = vehicleDetailPage;
    }

    @Override
    protected PAGE execute()
    {
        AfterMarketAccessoriesGrid accessories = _page.getAfterMarketAccessories();
        accessories.setHasAfterMarketAccessories( "true" );

        AccessoryRow accessoryRow = accessories.getAccessoryAtRow( 1 );
        accessoryRow.setValues( "Accessory Name - 1", "500" );
        accessories.addAccessory( "Accessory Name - 2", "1000" );
        accessories.addAccessory( "Accessory Name delete", "500" );
        accessories.getNumberOfRows();
        assertEquals( "$2,000", accessories.getTotalAccessoryValue() );
        accessories.deleteAfterMarketAccessories( 3 );
        assertEquals( "$1,500", accessories.getTotalAccessoryValue() );
        accessories.setHasAfterMarketAccessories( "false" );
        assertTrue( Element.isHidden( getWebDriver(), AfterMarketAccessoriesGrid.TABLE_LOCATOR ) );
        accessories.setHasAfterMarketAccessories( "true" );
        assertEquals( "$1,500", accessories.getTotalAccessoryValue() );

        AfterMarketModificationsGrid modifications = _page.getAfterMarketModifications();
        modifications.setHasAfterMarketModifications( "true" );

        ModificationRow modificationRow = modifications.getModificationAtRow( 1 );
        modificationRow.setValues( "Modification Name - 1", "1500" );
        modifications.addModification( "Modification Name - 2", "100" );
        modifications.addModification( "Modification Name delete", "1500" );
        assertEquals( "$3,100", modifications.getTotalModificationsValue() );
        modifications.getNumberOfRows();
        modifications.deleteAfterMarketModification( 3 );
        assertEquals( "$1,600", modifications.getTotalModificationsValue() );
        modifications.setHasAfterMarketModifications( "false" );
        modifications.setHasAfterMarketModifications( "true" );
        assertEquals( "$1,600", modifications.getTotalModificationsValue() );
        return _page;
    }
}
