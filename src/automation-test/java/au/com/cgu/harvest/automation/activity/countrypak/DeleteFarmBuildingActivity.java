package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Delete BuildingType in Farm Property Activity" )
public class DeleteFarmBuildingActivity extends AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyPage _page;
    private int _rowNumber;

    public DeleteFarmBuildingActivity( FarmPropertyPage farmPropertyPage, int rowNumber )
    {
        _page = farmPropertyPage;
        _rowNumber = rowNumber;
    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyPage farmPropertyPage =
            _page.getFarmPropertyBuildingTypeGrid().getFarmPropertyBuildingTypeAtRow( _rowNumber )
                .deleteBuildingType( true );

        return new FarmPropertyPage( getWebDriver() );
    }
}
