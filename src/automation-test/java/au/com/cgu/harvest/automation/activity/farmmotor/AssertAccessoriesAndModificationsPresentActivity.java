package au.com.cgu.harvest.automation.activity.farmmotor;

import static org.junit.Assert.assertEquals;
import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketAccessoriesGrid;
import au.com.cgu.harvest.pages.farmmotor.AfterMarketModificationsGrid;
import au.com.cgu.harvest.pages.farmmotor.IPageWithAccessoriesAndModifications;

@Activity( "Assert Accessories and Modifications Grid present with values after Suspend Activity" )
public class AssertAccessoriesAndModificationsPresentActivity< PAGE extends IPageWithAccessoriesAndModifications >
    extends AbstractActivity< PAGE >
{
    private PAGE _page;

    public AssertAccessoriesAndModificationsPresentActivity( PAGE vehicleDetailPage )
    {
        _page = vehicleDetailPage;
    }

    @Override
    protected PAGE execute()
    {
        AfterMarketAccessoriesGrid accessories = _page.getAfterMarketAccessories();

        assertEquals( "$1,500", accessories.getTotalAccessoryValue() );

        AfterMarketModificationsGrid modifications = _page.getAfterMarketModifications();

        assertEquals( "$1,600", modifications.getTotalModificationsValue() );
        return _page;
    }
}
