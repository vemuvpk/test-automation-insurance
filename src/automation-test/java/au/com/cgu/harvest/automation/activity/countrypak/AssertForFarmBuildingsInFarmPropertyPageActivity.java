package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyBuildingTypeRow;
import au.com.cgu.harvest.pages.countrypak.FarmPropertyPage;

@Activity( "Assert Added buildings are present on the Farm Property Page Activity" )
public class AssertForFarmBuildingsInFarmPropertyPageActivity extends
    AbstractActivity< FarmPropertyPage >
{
    private FarmPropertyPage _page;
    private int _rowNumber;
    private String _buildingType;
    private String _constructionType;
    private String _replacement;
    private String _buildingSumInsured;
    private String _contentsSumInsured;
    private String _specifiedContentsSumInsured;

    public AssertForFarmBuildingsInFarmPropertyPageActivity( FarmPropertyPage farmPropertyPage,
        int rowNumber, String buildingType, String constructionType, String replacement,
        String buildingSumInsured, String contentsSumInsured )
    {
        _page = farmPropertyPage;
        _rowNumber = rowNumber;
        _buildingType = buildingType;
        _constructionType = constructionType;
        _replacement = replacement;
        _buildingSumInsured = buildingSumInsured;
        _contentsSumInsured = contentsSumInsured;

    }

    @Override
    protected FarmPropertyPage execute()
    {
        FarmPropertyBuildingTypeRow farmBuildingRow =
            _page.getFarmPropertyBuildingTypeGrid().getFarmPropertyBuildingTypeAtRow( _rowNumber );

        farmBuildingRow.hasValues( _buildingType, _constructionType, _replacement,
            _buildingSumInsured, _contentsSumInsured );

        return new FarmPropertyPage( getWebDriver() );
    }
}
