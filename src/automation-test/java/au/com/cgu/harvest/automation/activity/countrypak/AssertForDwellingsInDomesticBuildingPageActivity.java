package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.countrypak.DomesticBuildingAndContentsPage;
import au.com.cgu.harvest.pages.countrypak.DwellingRow;

@Activity( "Assert Added/Edited Dwellings are present on the Domestic Buildings Page Activity" )
public class AssertForDwellingsInDomesticBuildingPageActivity extends
    AbstractActivity< DomesticBuildingAndContentsPage >
{
    private DomesticBuildingAndContentsPage _page;
    private int _rowNumber;
    private String _dwellingType;
    private String _year;
    private String _constructionType;
    private String _buildingSumInsured;
    private String _contentsSumInsured;
    private String _specifiedContentsSumInsured;

    public AssertForDwellingsInDomesticBuildingPageActivity(
        DomesticBuildingAndContentsPage domesticBuildingAndContentsPage,
        int rowNumber, String dwellingType, String year, String constructionType,
        String buildingSumInsured, String contentsSumInsured, String specifiedContentsSumInsured )
    {
        _page = domesticBuildingAndContentsPage;
        _rowNumber = rowNumber;
        _dwellingType = dwellingType;
        _year = year;
        _constructionType = constructionType;
        _buildingSumInsured = buildingSumInsured;
        _contentsSumInsured = contentsSumInsured;
        _specifiedContentsSumInsured = specifiedContentsSumInsured;
    }

    @Override
    protected DomesticBuildingAndContentsPage execute()
    {
        DwellingRow dwellingRow =
            _page.getDwellingGrid().getDwellingAtRow( _rowNumber );

        dwellingRow.hasValues( _dwellingType, _year, _constructionType,
            _buildingSumInsured, _contentsSumInsured, _specifiedContentsSumInsured );

        return new DomesticBuildingAndContentsPage( getWebDriver() );
    }
}
